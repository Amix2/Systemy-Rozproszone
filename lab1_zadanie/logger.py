import socket
import struct
import sys
from random import randint

serverPort = 9009
multicast_ip = '224.2.2.2'
serverIP = "127.0.0." + str(randint(2,20))

def create_socket(multicast_ip, port):
    local_ip = serverIP
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_IF, socket.inet_aton(local_ip))
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 2) # time to live
    membership_request = socket.inet_aton(multicast_ip) + socket.inet_aton(local_ip) # info dla routera o multicaście
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, membership_request)
    my_socket.bind((local_ip, port))
    my_socket.settimeout(5)

    return my_socket

serverSocket = create_socket(multicast_ip, serverPort)

buff = []

print('PYTHON LOGGER SERVER')
print(serverSocket.getsockname())
while True:
    try:
        buff, address = serverSocket.recvfrom(1024)
        print(str(buff,  "UTF-8").rstrip('\x00'))
    except:
        print("")

