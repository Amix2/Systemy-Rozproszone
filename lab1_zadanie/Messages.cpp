#include <iostream>
#include "Messages.h"

void print(Message msg) {
    printf("Message Type:%d; Token:%d::\n\tfrom %s; to %s\n\t=> %s\n", 
    msg.type, msg.token, 
    msg.senderName, msg.receiverName,
    msg.data.text);
}






UnknownMessage createMessageFromBytes(char* bytes, size_t size) {
    UnknownMessage msg;
    msg.type = getMsgType(bytes, size);
    memcpy(&(msg.contentLength), bytes+enumSize, sizeof(msg.contentLength));
    msg.content = new char[msg.contentLength];
    memcpy(msg.content, bytes+enumSize+sizeof(msg.contentLength), msg.contentLength);
    return msg;
}

UnknownMessage createMessageText(char text[1024], char destination[ClientNameLength], char source[ClientNameLength]) {
    UnknownMessage msg;
    TextMessage content;
    msg.type = TEXT;
    msg.contentLength = 1024+2*ClientNameLength;
    memcpy(content.destination, destination, ClientNameLength);
    memcpy(content.source, source, ClientNameLength);
    memcpy(content.text, text, 1024);
    msg.content = new char[msg.contentLength];
    memcpy(msg.content, &content, sizeof(content));
    return msg;
}

void printTextMessage(UnknownMessage msg) {
    TextMessage* content = (TextMessage*)msg.content;
    printf("TEXT MSG, type:%d; len:%d, dest: %s; source: %s\n\t%s\n", 
        msg.type, msg.contentLength, content->destination, content->source, content->text);
}

char* exportMessageToBytes(size_t* size, UnknownMessage message) {
    char* bytes = new char[getMessageByteSize(message)];
    messageType mType = message.type;
    memcpy(bytes, &mType, enumSize);
    memcpy(bytes+enumSize, &(message.contentLength), sizeof(message.contentLength));
    memcpy(bytes+enumSize+sizeof(message.contentLength), message.content, message.contentLength);
    
    *size = getMessageByteSize(message);
    return bytes;
}

size_t getMessageByteSize(UnknownMessage msg) {
    return (size_t)sizeof(msg.contentLength) + sizeof(msg.type) + msg.contentLength;
}

messageType getMsgType(char* bytes, size_t size) {
    messageType mType;
    memcpy(&mType, bytes, sizeof(messageType));
    return mType;
}

size_t getTextLengthFromTextMsg(char bytes[], size_t size) {
    return size - enumSize;
}

void makeTextMsg(char* bytes, size_t* size, char* msg, size_t msgLen) {
    messageType mType = TEXT;
    bytes = new char[msgLen + sizeof(messageType)];
    memcpy(bytes, &mType, sizeof(messageType));
    memcpy(bytes + sizeof(messageType), msg, msgLen);
}