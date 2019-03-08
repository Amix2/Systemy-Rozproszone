#include <Ws2tcpip.h>
#include "MySocket.h"


// UTPSocket
UTPSocket::UTPSocket(int port, char* ipAddr) {
    printf("UTPSocket constr\n");
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
    mySocket = socket(AF_INET,SOCK_DGRAM,0);
    struct sockaddr_in sockInfo;
    sockInfo.sin_family = AF_INET;
    sockInfo.sin_port = htons(port);
    sockInfo.sin_addr.s_addr = inet_addr(ipAddr);

    if (bind(mySocket, (struct sockaddr *) &sockInfo, sizeof(sockInfo)) == -1)
        printf("bind error\n");
    
    struct sockaddr_in sockRealInfo;
    int len = sizeof(sockRealInfo);
    if (getsockname(mySocket, (struct sockaddr *) &sockRealInfo, &(len)) == -1)
        printf("getsockname error\n");

    strcpy(myIP, inet_ntoa(sockRealInfo.sin_addr));
	myPort = ntohs(sockRealInfo.sin_port);

    printf("Socket UDP: IP: %s port: %d\n", myIP, myPort);
}
int UTPSocket::send(std::string str, char addr[], int port) {
    return UTPSocket::send(&(str[0]), addr, port);
}
int UTPSocket::send(char msg[], char addr[], int port, size_t size) {
    UnknownMessage msg3 = createMessageFromBytes(msg, 0);
    printTextMessage(msg3);
    struct sockaddr_in recvInfo;
    recvInfo.sin_family = AF_INET;
    recvInfo.sin_port = htons(port);
    recvInfo.sin_addr.s_addr = inet_addr(addr);

    char buffer[2048];
    ZeroMemory(buffer, sizeof(buffer));
    if(size == -1)
        strcpy(buffer, msg);
    else
        memcpy(buffer, msg, size);
    printf("UTPSocket sending to %s : %d\n\t-> %s\n", addr, port, msg);
    sendto(mySocket, buffer, sizeof(buffer), 0, (sockaddr*)&recvInfo, sizeof(recvInfo));
    UnknownMessage msg2 = createMessageFromBytes(buffer, 0);
    printTextMessage(msg2);
    return(1);
}


int UTPSocket::send(UnknownMessage msg, char addr[], int port) {
    printf("UTPSocket::send UnknownMessage\n");
    size_t s;
    char* bytes = exportMessageToBytes(&s, msg);
    UnknownMessage msg2 = createMessageFromBytes(bytes, 0);
    printTextMessage(msg2);
    UTPSocket::send(bytes, addr, port, s);
}
int UTPSocket::send(Message msg, char addr[], int port) {
    printf("UTPSocket::send message\n");
    struct sockaddr_in recvInfo;
    recvInfo.sin_family = AF_INET;
    recvInfo.sin_port = htons(port);
    recvInfo.sin_addr.s_addr = inet_addr(addr);
    sendto(mySocket, (char*)&msg, sizeof(msg), 0, (sockaddr*)&recvInfo, sizeof(recvInfo));
}

UnknownMessage recvMessageUNUSED() {
    int iResult = 0;
    char bytes[2048];
    sockaddr_in senderAddr;
    int SenderAddrSize = sizeof (senderAddr);
    iResult = recvfrom(mySocket, bytes, 2048, 0, (sockaddr *) &senderAddr, &SenderAddrSize);
    if (iResult == SOCKET_ERROR) {
        printf("UTPSocket recvMessage recvfrom error:: %d\n", WSAGetLastError());
    }

    UnknownMessage msg = createMessageFromBytes(bytes, 0);
    printTextMessage(msg);

    return msg;
}

Message UTPSocket::recvMessage() {
    int iResult = 0;
    Message msg;
    sockaddr_in senderAddr;
    int SenderAddrSize = sizeof (senderAddr);
    iResult = recvfrom(mySocket, (char*)(&msg), 2048, 0, (sockaddr *) &senderAddr, &SenderAddrSize);
    if (iResult == SOCKET_ERROR) {
        printf("UTPSocket recvMessage recvfrom error:: %d\n", WSAGetLastError());
    }
    return msg;
}


UTPSocket::~UTPSocket() {
    closesocket(mySocket);
    WSACleanup();
}

//#################################################
//Logger

Logger::Logger() {
}
int Logger::log(std::string msg) {
    return(logSocket.send(msg, (char*)logAddr, logPort));
}
int Logger::log(char msgChar[]) {
    return(logSocket.send(msgChar, (char*)logAddr, logPort));
}