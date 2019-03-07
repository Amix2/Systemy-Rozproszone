#include <winsock2.h>
#include <ws2tcpip.h>
#include <iostream>
#include <string>
#include "UTPSocket.h"

#define logAddr "224.2.2.2"
#define logPort 9009

class Logger {
        UTPSocket logSocket;
    public:
        Logger();
        int log(std::string msg);
        int log(char msgChar[]);
        
};

Logger::Logger() {
}
int Logger::log(std::string msg) {
    return(logSocket.send(msg, logAddr, logPort));
}
int Logger::log(char msgChar[]) {
    return(logSocket.send(msgChar, logAddr, logPort));
}
