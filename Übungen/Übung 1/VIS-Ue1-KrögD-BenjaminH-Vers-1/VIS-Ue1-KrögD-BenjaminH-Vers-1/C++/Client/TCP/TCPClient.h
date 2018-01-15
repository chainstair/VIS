//
// Created by David on 07.01.2018.
//

#ifndef EX01_TCPCLIENT_H
#define EX01_TCPCLIENT_H

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <semaphore.h>

#define BUFFER_SIZE 1024

using namespace std;

class TCPClient {
public:

    int mClientSocket;

    TCPClient();
    ~TCPClient();

    int initializeSocket(int _portNumber, char* _serverIPAddress, bool _IPv6);
    char* receiveMessage(int _communicationSocket);
    int sendMessage(int _communicationSocket, const char* _message);
    int closeSocket(int _communicationSocket);

};


#endif //EX01_TCPCLIENT_H
