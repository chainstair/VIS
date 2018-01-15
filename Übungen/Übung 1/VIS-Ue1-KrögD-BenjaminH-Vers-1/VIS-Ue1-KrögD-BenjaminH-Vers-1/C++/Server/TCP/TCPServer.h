//
// Created by David on 18.12.2017.
//

#ifndef EX01_TCPSERVER_H
#define EX01_TCPSERVER_H

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <semaphore.h>
#include "LightSensor.h"
#include "NoiseSensor.h"
#include "AirSensor.h"



#define BUFFER_SIZE 1024

class TCPServer {

public:

    int serverSocket;
    int mCommunicationSocket;

    TCPServer();
    ~TCPServer();

    int initializeSocket(int _portNumber, bool _IPv6);
    char* receiveMessage(int _communicationSocket, int _bufferSize);
    int sendMessage(int _communicationSocket, const char* _message);
    int closeSocket(int _communicationSocket);

private:
    char mReceivedMessage[BUFFER_SIZE];
    sockaddr_in mClientAddr;

    typedef struct {
        int socket;
        TCPServer* tcpServer;
        LightSensor* lightSensor;
        NoiseSensor* noiseSensor;
        AirSensor* airSensor;
    }socketParam;

    //int mConnectedClients;
};


#endif //EX01_TCPSERVER_H
