echo "Run start"
g++ -std=c++0x -g main.cpp Messages.cpp MySocket.cpp -lwsock32 -o main
echo "Run end"