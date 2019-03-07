#include "UTPSocket.h"

UTPSocket::UTPSocket() {
    printf("UTPSocket constr\n");
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
    mySocket = socket(AF_INET,SOCK_DGRAM,0);
    printf("Socket: %d\n", mySocket);
}
int UTPSocket::send(std::string str, char addr[], int port) {
    return UTPSocket::send(&(str[0]), addr, port);
}
int UTPSocket::send(char msg[], char addr[], int port) {
    struct sockaddr_in recvInfo;
    recvInfo.sin_family = AF_INET;
    recvInfo.sin_port = htons(port);
    recvInfo.sin_addr.s_addr = inet_addr(addr);

    char buffer[1024];
    ZeroMemory(buffer, sizeof(buffer));
    strcpy(buffer, msg);
    printf("UTPSocket sending %s\n", msg);
    sendto(mySocket, buffer, sizeof(buffer), 0, (sockaddr*)&recvInfo, sizeof(recvInfo));
    return(1);
}

UTPSocket::~UTPSocket() {
    closesocket(mySocket);
    WSACleanup();
}