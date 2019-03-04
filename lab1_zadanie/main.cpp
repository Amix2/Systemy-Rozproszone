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
using namespace std;

void InitWinsock()
{
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
}

int main(int argc, char* argv[])
{
    cout<<"main start"<<endl;
    SOCKET socketC;
    char addr[] = "224.2.2.2";

    InitWinsock();
    struct sockaddr_in serverInfo;
    int len = sizeof(serverInfo);
    serverInfo.sin_family = AF_INET;
    serverInfo.sin_port = htons(9009);
    serverInfo.sin_addr.s_addr = inet_addr(addr);

    socketC = socket(AF_INET,SOCK_DGRAM,0);
    cout<<socketC<<endl;
    for(int i=0; i<10; i++) {
        string msg = "wiadmocs " + to_string(i);
        char buffer[1024];
        ZeroMemory(buffer, sizeof(buffer));
	    strcpy(buffer, msg.c_str());
        cout<<"msg: "<<buffer<<endl;

        if (sendto(socketC, buffer, sizeof(buffer), 0, (sockaddr*)&serverInfo, len) != SOCKET_ERROR)
        {
            cout<<"wysłano9"<<endl;
        }
        Sleep(1000);
    }
    closesocket(socketC);
    WSACleanup();

    return 0;
}