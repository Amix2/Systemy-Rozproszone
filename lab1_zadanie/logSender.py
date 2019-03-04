import socket;

serverIP = "127.0.0.1"
multicast_group = ('224.2.2.2', 9009)
serverPort = 9009
group = (serverIP, serverPort)
msg = "mes"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'cp1250'), multicast_group)

#print("python udp client received msg: " + str(buff, 'cp1250'))




