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
#include <stdio.h>
#include <string>

#include "MySocket.h"
//#include "Messages.h"

using namespace std;


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

    cout<<"main start"<<endl;

    char addr[] = "224.2.2.2";
    int port = 9009;
    if(argc == 1) {
        UTPSocket sock;
        UnknownMessage uMsg = sock.recvMessage();
        printTextMessage(uMsg);
    } else {
        UTPSocket sock;
        char so[10] = "sourcIP";
        char de[10] = "destIP";
        char text[1024] = "wiadomosc";
        UnknownMessage uMsg = createMessageText(text, de, so);
        int port = atoi(argv[1]);
        cout<<"sending: "<<port<<" "<<argv[2]<<endl;
        printTextMessage(uMsg);
        sock.send(uMsg, argv[2], port);
    }
    // string msg = "wiadmocs " + to_string(1);

    // char* mesC = new char[10];
    // strcpy(mesC, (char*)&msg);
    // UTPSocket sock;
    // cout<<sock.send(msg, addr, port)<<endl;

    cout<<"Main end"<<endl;
    return 0;
}