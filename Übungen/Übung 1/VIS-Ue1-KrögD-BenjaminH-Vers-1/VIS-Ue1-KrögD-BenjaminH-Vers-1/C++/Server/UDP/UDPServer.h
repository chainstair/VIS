//
// Created by David on 08.01.2018.
//

#ifndef UDP_UDPSERVER_H
#define UDP_UDPSERVER_H

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <semaphore.h>

#define BUFFER_SIZE 1024

using namespace std;

class UDPServer {

public:

    UDPServer();
    ~UDPServer();

    int initializeSocket(int _portNumber);
    char* receiveMessage(int _udpSocket);
    int sendMessage(int _udpSocket, const char* _message, sockaddr_in _toAddress);
    int closeSocket(int _udpSocket);

private:
    char mReceivedMessage[BUFFER_SIZE];
    sockaddr_in mClientAddr;

};


#endif //UDP_UDPSERVER_H
