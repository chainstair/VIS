//
// Created by David on 08.01.2018.
//

#ifndef UDP_UDPCLIENT_H
#define UDP_UDPCLIENT_H

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <semaphore.h>

#define BUFFER_SIZE 1024

using namespace std;


class UDPClient {
public:

    UDPClient();
    ~UDPClient();

    int initializeSocket();
    char* receiveMessage(int _udpSocket);
    int sendMessage(int _udpSocket, const char* _message, int _portNumber, char* _serverIPAddress);
    int closeSocket(int _udpSocket);


};


#endif //UDP_UDPCLIENT_H
