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

#include "MySocket.h"
using namespace std;
const char defaultAddr[16] = "127.0.0.1";

UTPSocket myUDPSock;
char myAddr[16];
int myPort;
char myName[ClientNameLength];
bool hasToken;

enum KlientStates {
    ALONE = 1,
    HAS_TOKEN,
    NO_TOKEN
};

// args must: myName hasToken
// args extra: myPort, e-extra: myAddr;
void initMyself(int argc, char* argv[]);

Message makeTextMsg(char text[MaxTextLen], char recvAddr[16], int recvPort, char recvName[ClientNameLength]);

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
        Message msg = makeTextMsg("jakis text wiadomowasi", myAddr, myPort, myName);
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

void initMyself(int argc, char* argv[]) {
    if(argc < 2) {
        printf("Error: za malo arg\n");
        exit(1);
    }
    strcpy(myName, argv[1]);
    if(atoi(argv[3]) == 1) hasToken = true; else hasToken = false;
    if(argc >= 4) {
        myPort = atoi(argv[3]);
        if(argc == 5) {
            strcpy(myAddr, argv[4]);
        } else {
            strcpy(myAddr, defaultAddr);
        }
    } else {
        myPort = 0; //will be set after socket init
    }
    myUDPSock.init(myPort, myAddr);
    myPort = myUDPSock.myPort;

    printf("initMyself\n\tToken:%d, Name:%s, IP:%s :: %d\n", hasToken, myName, myAddr, myPort);
}

Message makeTextMsg(char text[MaxTextLen], char recvAddr[16], int recvPort, char recvName[ClientNameLength]) {
    Message msg;
    strcpy(msg.senderIP, myAddr);
    strcpy(msg.senderName, myName);
    msg.senderPort = myPort;
    strcpy(msg.receiverIP, recvAddr);
    strcpy(msg.receiverName, recvName);
    msg.receiverPort = recvPort;
    strcpy(msg.data.text, text);
    return msg;
}