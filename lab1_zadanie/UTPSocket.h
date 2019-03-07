#include <winsock2.h>
#include <ws2tcpip.h>
#include <iostream>
#include <string>

class UTPSocket {
    private:
        SOCKET mySocket;
    public:
        UTPSocket();
        ~UTPSocket();
        int send(std::string str, char addr[], int port);
        int send(char c[], char addr[], int port);

};