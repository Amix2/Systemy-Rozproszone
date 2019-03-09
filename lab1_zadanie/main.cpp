#ifndef UNICODE
#define UNICODE 1
#endif

// link with Ws2_32.lib
#pragma comment(lib,"Ws2_32.lib")

#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdio.h>
#include <stdlib.h>   // Needed for _wtoi
#include <iostream>
#include <winsock2.h>
#include <string>
#include <queue>

#include "MySocket.h"
using namespace std;
const char defaultAddr[16] = "127.0.0.1";
#define maxClientsNum 10

UTPSocket myUDPSock;
char myAddr[16];
int myPort;
char myName[ClientNameLength];
char neiAddr[16];
int neiPort;
char neiName[ClientNameLength];
bool hasToken;
bool isAlone = true;
queue <Message> msgQueue;
Message lastMsg;
enum ClientStates {
    HAS_TOKEN = 1,
    NO_TOKEN
};
ClientStates clientList[maxClientsNum];

// args must: myName hasToken myPort
// args extra: neiPort, e-extra: neiAddr;
void initMyself(int argc, char* argv[]);
// main.exe mojeImie 1 9009
//main.exe mojeImie 1 9009 1234

Message makeTextMsg(char text[MaxTextLen], char recvName[ClientNameLength]);
Message makeGlobalRegisterMsg(Message localRegisterMsg);
void printStatus();

int main(int argc, char* argv[])
{
    // char so[10] = "sourcIP";
    // char de[10] = "destIP";
    // char text[1024] = "wiadomosc";
    // UnknownMessage uMsg = createMessageText(text, de, so);
    // printTextMessage(uMsg);
    // char* bytes;
    // size_t size;
    // bytes = exportMessageToBytes(&size, uMsg);
    // UnknownMessage uMsg2 = createMessageFromBytes(bytes, size);
    // printTextMessage(uMsg2);

    cout<<"main start "<<argc<<endl;
    initMyself(argc, argv);
    while(true) {
        /*
            IF ( alone  )   wait for register msg
            IF ( !alone and !token ) wait for token msg
            IF ( !alone and token ) {
                IF ( token free ) take msg from queue and send it
                IF ( token used ) send msg with token to next
            }
        */
       printStatus();
       lastMsg = myUDPSock.recvMessage();
        if(hasToken && !isAlone) {
            if(lastMsg.type != FREE) {
                //send msg to nei
            } else {
                // I have token and can send my message
            }
        }
    }
    







    char addr[] = "224.2.2.2";
    int port = 9009;
    if(argc < 4) {
        UTPSocket sock(0);
        Message Msg = sock.recvMessage();

        print(Msg);
    } else {
        UTPSocket sock(0);
        char so[10] = "sourcIP";
        char de[10] = "destIP";
        char text[1024] = "wiadomosc";
        int port = atoi(argv[1]);
        cout<<"sending to: "<<port<<" "<<argv[2]<<endl;
        Message msg = makeTextMsg("jakis text wiadomowasi", myName);
        strcpy(msg.senderName, so);
        print(msg); 
        sock.send(msg, argv[2], port);
    }
    // string msg = "wiadmocs " + to_string(1);

    // char* mesC = new char[10];
    // strcpy(mesC, (char*)&msg);
    // UTPSocket sock;
    // cout<<sock.send(msg, addr, port)<<endl;

    cout<<"Main end"<<endl;
    return 0;
}

// args must: myName hasToken myPort
// args extra: neiPort, e-extra: neiAddr;
void initMyself(int argc, char* argv[]) {
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
        neiPort = -1;
        isAlone = true;
    }
    myUDPSock.init(myPort, myAddr);

    printf("initMyself\n\tToken:%d, Name:%s, IP:%s :: %d\n", hasToken, myName, myAddr, myPort);
    if(!isAlone) 
        printf("\tNei: Name:%s, IP:%s :: %d\n", neiName, neiAddr, neiPort);
}

Message makeTextMsg(char text[MaxTextLen], char recvName[ClientNameLength]) {
    printf("makeTextMsg: to %s, content: %s\n", recvName, text);
    Message msg;
    strcpy(msg.senderName, myName);
    strcpy(msg.receiverName, recvName);
    strcpy(msg.data.text, text);
    return msg;
}

Message makeGlobalRegisterMsg(Message localRegisterMsg) {
    printf("makeGlobalRegisterMsg\n\tNew: Name:%s, IP:%s :: %d\n", 
    localRegisterMsg.data.clientData.name, localRegisterMsg.data.clientData.IPaddr, localRegisterMsg.data.clientData.port);
}

void printStatus() {
    printf("Status: Token:%d, Name:%s, IP:%s :: %d\n", hasToken, myName, myAddr, myPort);
    if(!isAlone) 
        printf("\tNei: Name:%s, IP:%s :: %d\n", neiName, neiAddr, neiPort);
    else
        printf("\tAlone\n");
    printf("\tMsgQueue.size: %d\n", msgQueue.size());
}