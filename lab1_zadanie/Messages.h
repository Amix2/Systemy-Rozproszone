#include <iostream>
#define ClientNameLength 10
#define MaxTextLen 1024

enum messageType {
    FREE,
    TEXT,
    REGISTER_NEW,
    LOCAL_REGISTER_NEW
};
const size_t enumSize = sizeof(messageType);

struct Message {
    messageType type;
    bool token; // 0->NO; 1->YES
    int reserved = 0;
    char senderName[ClientNameLength];
    char receiverName[ClientNameLength];
    union {
        char text[MaxTextLen];
        struct { // for new register
            char name[ClientNameLength];
            char IPaddr[16];
            int port;
        } clientData;
    } data;
};

void print(Message msg);
