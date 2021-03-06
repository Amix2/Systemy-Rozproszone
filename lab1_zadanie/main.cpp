

// link with Ws2_32.lib
//#pragma comment(lib,"Ws2_32.lib")

#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdio.h>
#include <stdlib.h>   // Needed for _wtoi
#include <iostream>
#include <Windows.h>
#include <string>
#include <queue>
#include <time.h>
#include "mingw.thread.h"

#include "MySocket.h"
using namespace std;
const char defaultAddr[] = "127.0.0.1";
#define maxClientsNum 10
#define LOG(msg, ...) {cout<<to_string((GetTickCount()/100)%10000)<<" :"; printf(msg, ##__VA_ARGS__); }

UTPSocket myUDPSock;
Logger logger;
char myAddr[16];
int myPort;
char myName[ClientNameLength];
char neiAddr[16];
int neiPort;
char neiName[ClientNameLength];
bool hasToken;
bool isAlone = true;
enum {
    WAIT_FOR_MSG = 1,
    SEND_NEXT_MSG,
    HANDLE_CURRENT,
    GET_FROM_QUEUE
} InOutOption;
queue <Message> msgQueue;
Message *lastMsg = new Message;
bool reservedToken = false;

// args must: myName hasToken myPort
// args extra: neiPort, e-extra: neiAddr;
void initMyself(int argc, char* argv[]);
// main.exe clie 1 55005
// main.exe clie2 0 0 55005
// main.exe clie3 0 0 55005

void InitWinsock()
{
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
}

string logMsg(string str) {
    cout<<to_string((GetTickCount()/100)%10000) + " : " + myName + " ["+to_string(myPort)+"] " + str<<endl;
    return to_string((GetTickCount()/100)%10000) + " : " + myName + " ["+to_string(myPort)+"] " + str;
}

Message makeTextMsg(char text[MaxTextLen], char recvName[ClientNameLength]);
Message makeGlobalRegisterMsgFromLocal(Message localRegisterMsg);
Message makeLocalRegisterMsg();
Message makeFreeMsg();

int handleAnyAction(Message msg);
int handleAnyMsg(Message msg);
int handleRegisterMsg(Message regMsg); // 0 -> bez zmian | 1 -> zmiana sąsiadów
int handleTextMsg(Message msg); // 1-> do mnie
void printStatus();

void InputHandler() {
    while(true) {
        char recvName[ClientNameLength];
        char text[MaxTextLen];
        scanf("%s %s", recvName, text);
        Message textMsg = makeTextMsg(text, recvName);
        printf("New direct text msg:\n\t to %s :: %s\n", recvName, text);
        print(textMsg);
        msgQueue.push(textMsg);
    }
}

void cleanUp() {
    delete logger;
    delete myUDPSock;
}

int main(int argc, char* argv[])
{
    atexit(cleanUp);

    cout<<"main start"<<endl;
    InitWinsock();
    logger.init();
    printStatus();
    cout<<"main start "<<argc<<endl;
    string msgqq = "INTRO";
    logger.log(msgqq);


    initMyself(argc, argv);
    thread IOthread(InputHandler);
    printStatus();
    if(isAlone) {
        LOG("Im alone\n");
        InOutOption = WAIT_FOR_MSG;
    } else {
        LOG("Im not alone\n");
        Message tempMsg = makeLocalRegisterMsg();
        lastMsg = &tempMsg;
        print(*lastMsg);
        myUDPSock.send(*lastMsg, neiAddr, neiPort);
        InOutOption = WAIT_FOR_MSG;
    }
    while(true) {
       printStatus();
       handleAnyAction(*lastMsg);
       Sleep(1000);
    }
    cout<<"Main end"<<endl;

    return 0;
}

// args must: myName hasToken myPort
// args extra: neiPort, e-extra: neiAddr;
void initMyself(int argc, char* argv[]) {
    InitWinsock();
    logger.init();
    if(argc < 2) {
        printf("Error: za malo arg\n");
        exit(1);
    }
    strcpy(myName, argv[1]);
    if(atoi(argv[2]) == 1) hasToken = true; else hasToken = false;
    myPort = atoi(argv[3]);
    strcpy(myAddr, defaultAddr);
    // neighbour data
    if(argc >= 5) {
        isAlone = false;
        neiPort = atoi(argv[4]);
        if(argc == 6) {
            strcpy(neiAddr, argv[4]);
        } else {
            strcpy(neiAddr, defaultAddr);
        }
    } else {
        neiPort = myPort;
        strcpy(neiAddr, defaultAddr);
        strcpy(neiName, myName);
        isAlone = true;
    }
    myUDPSock.init(myPort, myAddr);
    myPort = myUDPSock.myPort;
    LOG("initMyself\n\tToken:%d, Name:%s, IP:%s :: %d\n", hasToken, myName, myAddr, myPort);
    if(!isAlone) 
        LOG("\tNei: Name:%s, IP:%s :: %d\n", neiName, neiAddr, neiPort);
}

Message makeTextMsg(char text[MaxTextLen], char recvName[ClientNameLength]) {
    LOG("makeTextMsg: to %s, content: %s\n", recvName, text);
    Message msg;
    msg.type = TEXT;
    msg.token = true;
    msg.reserved = -1;
    strcpy(msg.senderName, myName);
    strcpy(msg.receiverName, recvName);
    strcpy(msg.data.text, text);
    return msg;
}

Message makeGlobalRegisterMsgFromLocal(Message localRegisterMsg) {
    Message globalMsg(localRegisterMsg);
    LOG("makeGlobalRegisterMsgFromLocal\n\tNew: Name:%s, IP:%s :: %d\n", 
        globalMsg.data.clientData.name, globalMsg.data.clientData.IPaddr, globalMsg.data.clientData.port);
    globalMsg.token = true;
    globalMsg.type = REGISTER_NEW;
    strcpy(globalMsg.senderName, myName);
    isAlone = false;
    return globalMsg;
}

Message makeLocalRegisterMsg() {
    Message localRegisterMsg;
    localRegisterMsg.type = LOCAL_REGISTER_NEW;
    localRegisterMsg.token = hasToken;
    strcpy(localRegisterMsg.data.clientData.name, myName);
    strcpy(localRegisterMsg.data.clientData.IPaddr, myAddr);
    localRegisterMsg.data.clientData.port = myPort;
    LOG("makeLocalRegisterMsg\n\tNew: Name:%s, IP:%s :: %d\n", 
    localRegisterMsg.data.clientData.name, localRegisterMsg.data.clientData.IPaddr, localRegisterMsg.data.clientData.port);
    print(localRegisterMsg);
    return localRegisterMsg;
}

Message makeFreeMsg() {
    Message freeMsg;
    freeMsg.type = FREE;
    freeMsg.token = true;
    LOG("makeFreeMsg\n");
    print(freeMsg);
    return freeMsg;
}

int handleTextMsg(Message msg) {
    if(strcmp(msg.receiverName, myName) != 0 && strcmp(msg.senderName, myName) != 0) { // nie mnie dotyczy
        return 0;
    }
    printf("New Message:\n\t%s\n", msg.data.text);
    return 1;
}

int handleAnyAction(Message msg) {
    switch (InOutOption) {
        case WAIT_FOR_MSG:
            LOG("Waiting for msg\n")
            *lastMsg = myUDPSock.recvMessage();
            LOG("We have a Message\n");
            print(*lastMsg);
            if(lastMsg->token || hasToken) {
                LOG("With token\n")
                hasToken = true;
                InOutOption = HANDLE_CURRENT;
            } else {
                LOG("No token, add to queue and still wait\n")
                msgQueue.push(*lastMsg);
                InOutOption = WAIT_FOR_MSG;
            }
            break;
        case SEND_NEXT_MSG:
            LOG("Sending msg\n")
            hasToken = false;
            msg.token = true;
            myUDPSock.send(msg, neiAddr, neiPort);
            InOutOption = WAIT_FOR_MSG;
            break;
        case HANDLE_CURRENT:
            LOG("Handling current message\n");
            handleAnyMsg(msg);
            // ^ changes InOutOption
            break;
        case GET_FROM_QUEUE:
            if(msgQueue.empty()) {
                LOG("Queue empty, sending free msg\n");
                *lastMsg = makeFreeMsg();
                InOutOption = SEND_NEXT_MSG;
            } else {
                LOG("New msg from queue\n");
                *lastMsg = msgQueue.front();
                msgQueue.pop();
                InOutOption = HANDLE_CURRENT;
            }
            break;
        default:
            LOG("handleAnyAction switch (InOutOption) not handled %d\n", InOutOption);
    }
    return 0;
}

int handleAnyMsg(Message msg) {
    int iRes = 0;
    print(msg);
    switch(msg.type) {
        case FREE:
            if(!msg.token) LOG("error recv FREE msg with no token\n")
            else hasToken = true;
            logger.log(logMsg("FREE message"));
            LOG("Message Free, check queue\n");
            if(msg.reserved == 0) {
                InOutOption = GET_FROM_QUEUE;
                reservedToken = false;
            }
            else {
                if(reservedToken) {
                    InOutOption = GET_FROM_QUEUE;
                    reservedToken = false;
                } else {
                    InOutOption = SEND_NEXT_MSG;
                }
            }
            break;
        case LOCAL_REGISTER_NEW:
            if(msg.token) hasToken = true;
            LOG("New Local Register\n")
            logger.log(logMsg("LOCAL_REGISTER message"));
            *lastMsg = makeGlobalRegisterMsgFromLocal(msg);
            InOutOption = HANDLE_CURRENT;
            break;
        case REGISTER_NEW:
            if(!msg.token) LOG("error recv REGISTER_NEW msg with no token\n")
            else hasToken = true;
            logger.log(logMsg("GLOBAL_REGISTER message"));
            iRes = handleRegisterMsg(msg);
            switch (iRes) {
                case 0:
                    LOG("Reg Msg: Nothing to do\n");
                    InOutOption = SEND_NEXT_MSG;
                    break;
                case 1:
                    LOG("Reg Msg: its my message, terminating message\n");
                    InOutOption = GET_FROM_QUEUE;
                    break;
                case 2:
                    LOG("Reg Msg: my next changed\n")
                    InOutOption = SEND_NEXT_MSG;
                    break;
                default:
                    LOG("REGISTER_NEW switch (iRes) not handled %d\n", iRes);
            }
            if(!msgQueue.empty()) {
                lastMsg->reserved++;
                reservedToken = true;
            }
            break;
        case TEXT:
            logger.log(logMsg("TEXT message"));
            iRes = handleTextMsg(msg);
            switch (iRes) {
                case 0:
                    LOG("Text Msg not for me\n");
                    InOutOption = SEND_NEXT_MSG;
                    break;
                case 1:
                    LOG("Text Msg for me\n")
                    reservedToken = false;
                    if(lastMsg->reserved == -1) {
                        lastMsg->reserved = 0;
                        InOutOption = SEND_NEXT_MSG;
                        break;
                    }
                    InOutOption = GET_FROM_QUEUE;
                    break;
                default:
                    LOG("TEXT switch (iRes) not handled %d\n", iRes);

            }
            if(!msgQueue.empty()) {
                lastMsg->reserved++;
                reservedToken = true;
            }
            break;
        default:
            LOG("handleAnyMsg switch(msg.type) not handled %d\n", msg.type);
    }
    return 0;
}

int handleRegisterMsg(Message regMsg) {
    // 0 - ignore
    // 1 - terminate & adapt
    // 2 - adapt
    if(strcmp(regMsg.data.clientData.name, myName) == 0) {
        // its my message
        strcpy(neiName, regMsg.senderName);
        return 1;
    }
    if(strcmp(regMsg.senderName, neiName) != 0) { // nie mnie dotyczy
        return 0;
    } else {
        // remove connection to nei
        strcpy(neiName, regMsg.data.clientData.name);
        strcpy(neiAddr, regMsg.data.clientData.IPaddr);
        neiPort = regMsg.data.clientData.port;
        return 2;
    }

    return 0;
}

void printStatus() {
    printf("Status (%d): Token:%d, Name:%s, IP:%s :: %d\n", InOutOption, hasToken, myName, myAddr, myPort);
    if(!isAlone) 
        printf("\tNei: Name:%s, IP:%s :: %d\n", neiName, neiAddr, neiPort);
    else
        printf("\tAlone\n");
    printf("\tMsgQueue.size: %d\n", msgQueue.size());
}