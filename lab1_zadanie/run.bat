echo "Run start"
g++ -std=c++11 main.cpp Messages.cpp MySocket.cpp -o main -lwsock32
echo "Run end"