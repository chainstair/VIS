//
// Created by David on 08.01.2018.
//

#include "UDPServer.h"

UDPServer::UDPServer() {

}

UDPServer::~UDPServer() {

}

int UDPServer::initializeSocket(int _portNumber) {
    int serverSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (serverSocket == -1) {
        cout << "Error: init" << endl;
        closeSocket(serverSocket);
        exit(serverSocket);
    }

    sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(_portNumber);
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    memset(&(serverAddr.sin_zero), '\0', 8);

    int bindingReturnValue = bind(serverSocket, (sockaddr *) &serverAddr, sizeof(serverAddr));
    if (bindingReturnValue == -1) {
        cout << "Error: bind" << endl;
        closeSocket(serverSocket);
        exit(bindingReturnValue);
    }
    return serverSocket;
}

char *UDPServer::receiveMessage(int _udpSocket) {
    char message[BUFFER_SIZE];
    sockaddr_in from;
    int fromSize = sizeof(from);
    bool readyToReceive = true;

    cout << "Waiting for message..." << endl;

    while (readyToReceive) {
        int receiveReturnValue = recvfrom(_udpSocket, message, BUFFER_SIZE, 0, (sockaddr *) &from, &fromSize);
        if (receiveReturnValue == -1) {
            cout << "Error: receive" << endl;
            readyToReceive = false;
            exit(receiveReturnValue);
        } else {
            cout << "Received " << receiveReturnValue << " Bytes." << endl;
            string echo = "ECHO: ";
            string echoMessageString = echo + message;
            const char *echoMessage = echoMessageString.c_str();
            sendMessage(_udpSocket, echoMessage, from);
        }
    }

}


int UDPServer::sendMessage(int _udpSocket, const char *_message, sockaddr_in _toAddress) {
    int messageSize = strlen(_message) +1;
    int toSize = sizeof(_toAddress);

    int sendReturnValue = sendto(_udpSocket, _message, messageSize, 0, (sockaddr*) &_toAddress, toSize);
    if (sendReturnValue == -1) {
        cout << "Error: send" << endl;
        exit(sendReturnValue);
    } else {
        cout << "Sent " << sendReturnValue << "Bytes." << endl;
    }

    return sendReturnValue;
}

int UDPServer::closeSocket(int _communicationSocket) {
    return 0;
}
