import socket
import struct
import sys

serverPort = 9009
multicast_ip = '224.2.2.2'
serverIP = "127.0.0.1"

def create_socket(multicast_ip, port):
    """
    Creates a socket, sets the necessary options on it, then binds it. The socket is then returned for use.
    """

    local_ip = serverIP

    # create a UDP socket
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # allow reuse of addresses
    my_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    # set multicast interface to local_ip
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_IF, socket.inet_aton(local_ip))

    # Set multicast time-to-live to 2...should keep our multicast packets from escaping the local network
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 2)

    # Construct a membership request...tells router what multicast group we want to subscribe to
    membership_request = socket.inet_aton(multicast_ip) + socket.inet_aton(local_ip)

    # Send add membership request to socket
    # See http://www.tldp.org/HOWTO/Multicast-HOWTO-6.html for explanation of sockopts
    my_socket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, membership_request)

    # Bind the socket to an interface.
    # If you bind to a specific interface on the Mac, no multicast data will arrive.
    # If you try to bind to all interfaces on Windows, no multicast data will arrive.
    # Hence the following.
    my_socket.bind((local_ip, port))
    my_socket.settimeout(5)

    return my_socket

serverSocket = create_socket(multicast_ip, serverPort)

buff = []

print('PYTHON UDP SERVER')
print(serverSocket.getsockname())
while True:
    try:
        buff, address = serverSocket.recvfrom(1024)
        print("python udp server received msg: ")
        print(str(buff,  "UTF-8").rstrip('\x00'))
        print(address)
    except:
        print("timeout")

