#include <Ws2tcpip.h>
#include "MySocket.h"

#define LOG(msg, ...) printf(msg, ##__VA_ARGS__);

// UTPSocket
UTPSocket::UTPSocket(int port, char* ipAddr) {
    LOG("UTPSocket constr\n");
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
    mySocket = socket(AF_INET,SOCK_DGRAM,0);
    struct sockaddr_in sockInfo;
    sockInfo.sin_family = AF_INET;
    sockInfo.sin_port = htons(port);
    sockInfo.sin_addr.s_addr = inet_addr(ipAddr);

    if (bind(mySocket, (struct sockaddr *) &sockInfo, sizeof(sockInfo)) == -1)
        LOG("bind error\n");
    
    struct sockaddr_in sockRealInfo;
    int len = sizeof(sockRealInfo);
    if (getsockname(mySocket, (struct sockaddr *) &sockRealInfo, &(len)) == -1) {
        LOG("getsockname error\n");

    }

    strcpy(myIP, inet_ntoa(sockRealInfo.sin_addr));
	myPort = ntohs(sockRealInfo.sin_port);

    LOG("Socket UDP: IP: %s port: %d\n", myIP, myPort);
}

int UTPSocket::send(Message msg, char addr[], int port) {
    LOG("UTPSocket::send message to nei: %s : %d\n", addr, port);
    print(msg);
    struct sockaddr_in recvInfo;
    recvInfo.sin_family = AF_INET;
    recvInfo.sin_port = htons(port);
    recvInfo.sin_addr.s_addr = inet_addr(addr);
    sendto(mySocket, (char*)&msg, sizeof(msg), 0, (sockaddr*)&recvInfo, sizeof(recvInfo));
}

Message UTPSocket::recvMessage() {
    int iResult = 0;
    Message msg;
    sockaddr_in senderAddr;
    int SenderAddrSize = sizeof (senderAddr);
    iResult = recvfrom(mySocket, (char*)(&msg), sizeof(msg), 0, (sockaddr *) &senderAddr, &SenderAddrSize);
    if (iResult == SOCKET_ERROR) {
        LOG("UTPSocket recvMessage recvfrom error:: %d\n", WSAGetLastError());
    }
    print(msg);
    return msg;
}

void UTPSocket::init(int port, char* ipAddr) {
    LOG("UTPSocket init\n");
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
    mySocket = socket(AF_INET,SOCK_DGRAM,0);
    struct sockaddr_in sockInfo;
    sockInfo.sin_family = AF_INET;
    sockInfo.sin_port = htons(port);
    sockInfo.sin_addr.s_addr = inet_addr(ipAddr);

    if (bind(mySocket, (struct sockaddr *) &sockInfo, sizeof(sockInfo)) == -1)
        LOG("bind error\n");
    
    struct sockaddr_in sockRealInfo;
    int len = sizeof(sockRealInfo);
    if (getsockname(mySocket, (struct sockaddr *) &sockRealInfo, &(len)) == -1)
        LOG("getsockname error\n");

    //strcpy(myIP, inet_ntoa(sockRealInfo.sin_addr));
	myPort = ntohs(sockRealInfo.sin_port);

    LOG("Socket UDP init: port: %d, addr: %s\n", myPort, ipAddr);
}




int UTPSocket::send(std::string str, char addr[], int port) {
    return UTPSocket::send(str.c_str(), addr, port);
}
int UTPSocket::send(char msg[], char addr[], int port, size_t size) {
    struct sockaddr_in recvInfo;
    recvInfo.sin_family = AF_INET;
    recvInfo.sin_port = htons(port);
    recvInfo.sin_addr.s_addr = inet_addr(addr);
    char buffer[MaxTextLen];
    ZeroMemory(buffer, sizeof(buffer));
    if(size == -1)
        strcpy(buffer, msg);
    else
        memcpy(buffer, msg, size);
    printf("UTPSocket sending to %s : %d\n\t-> %s\n", addr, port, msg);
    sendto(mySocket, buffer, sizeof(buffer), 0, (sockaddr*)&recvInfo, sizeof(recvInfo));
    return(1);
}

UTPSocket::~UTPSocket() {
    closesocket(mySocket);
    WSACleanup();
}

//#################################################
//Logger

Logger::Logger() {
}
Logger::~Logger() {
    closesocket(socketC);
}
void Logger::init() {
    socketC = socket(AF_INET,SOCK_DGRAM,0);
}
int Logger::log(std::string msg) {
    struct sockaddr_in serverInfo;
    int len = sizeof(serverInfo);
    serverInfo.sin_family = AF_INET;
    serverInfo.sin_port = htons(logPort);
    serverInfo.sin_addr.s_addr = inet_addr(logAddr);

    char buffer[1024];
    ZeroMemory(buffer, sizeof(buffer));
	strcpy(buffer, msg.c_str());
    LOG("logger buffer: %s\n",buffer);
    sendto(socketC, buffer, sizeof(buffer), 0, (sockaddr*)&serverInfo, len);
}