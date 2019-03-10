#include <iostream>
#include "Messages.h"

void print(Message msg) {
    printf("Message (%d) Type:%d; Token:%d::\n\tfrom %s; to %s\n\t=> %s\n", 
    msg.reserved, msg.type, msg.token, 
    msg.senderName, msg.receiverName,
    msg.data.text);
}
