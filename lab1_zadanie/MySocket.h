#include <winsock2.h>
#include <ws2tcpip.h>
#include <winsock.h>
#include <Winsock2.h>
#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <stdio.h> 
#include "Messages.h"
#pragma comment(lib, "ws2_32.lib")

#define logAddr "224.2.2.2"
#define logPort 9009


class UTPSocket {
    private:
        SOCKET mySocket;
        char myIP[16];
        int myPort;
    public:
        UTPSocket(int port = 0, char* ipAddr = (char*)"127.0.0.1");
        ~UTPSocket();
        int send(std::string str, char addr[], int port);
        int send(char c[], char addr[], int port, size_t size = -1);
        int send(UnknownMessage msg, char addr[], int port);
        int send(Message msg, char addr[], int port);
        UnknownMessage recvMessage();
        Message recvMessage();


};

class Logger {
        UTPSocket logSocket;
    public:
        Logger();
        int log(std::string msg);
        int log(char msgChar[]);
        
};