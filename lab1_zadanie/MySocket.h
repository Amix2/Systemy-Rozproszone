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
        char myIP[16]; // to remove
    public:
        int myPort;
        UTPSocket(int port, char* ipAddr = (char*)"127.0.0.1");
        UTPSocket() {};
        ~UTPSocket();
        void init(int port = 0, char* ipAddr = (char*)"127.0.0.1");
        int send(std::string str, char addr[], int port);
        int send(char c[], char addr[], int port, size_t size = -1);
        int send(Message msg, char addr[], int port);
        Message recvMessage();
};

class Logger {
        SOCKET socketC;
    public:
        Logger();
        void init();
        int log(std::string msg);       
};