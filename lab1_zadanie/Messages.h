#include <iostream>
#define ClientNameLength 10

enum messageType {
    TOKEN_FREE = 1,
    TOKEN_USED,
    TEXT
};
const size_t enumSize = sizeof(messageType);

struct Message {
    messageType type;
    size_t contentLength;
    char* content;
};
struct UnknownMessage {
    messageType type;
    size_t contentLength;
    char* content;
}; 
struct TextMessage {
    char destination[ClientNameLength];
    char source[ClientNameLength];
    char text[1024];
};

UnknownMessage createMessageFromBytes(char* bytes, size_t size);
UnknownMessage createMessageText(char text[1024], char destination[10], char source[10]);
char* exportMessageToBytes(size_t* size, UnknownMessage message);
void printTextMessage(UnknownMessage msg);
size_t getMessageByteSize(UnknownMessage msg);

messageType getMsgType(UnknownMessage message);
messageType getMsgType(char* bytes, size_t size);
size_t getMessageContentSize(char* bytes, size_t size);

char* getTextFromTextMsg(UnknownMessage message);
size_t getTextLengthFromTextMsg(UnknownMessage message);
void makeTextMsg(char* bytes, size_t* size, char* msg, size_t msgLen);
