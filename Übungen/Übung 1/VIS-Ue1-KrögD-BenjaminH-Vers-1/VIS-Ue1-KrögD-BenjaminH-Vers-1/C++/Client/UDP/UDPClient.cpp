//
// Created by David on 08.01.2018.
//

#include "UDPClient.h"

UDPClient::UDPClient() {

}

UDPClient::~UDPClient() {

}

int UDPClient::initializeSocket() {
    int clientSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (clientSocket == -1) {
        cout << "Error: initialize client socket" << endl;
        exit(clientSocket);
    }
    return clientSocket;
}

char *UDPClient::receiveMessage(int _udpSocket) {
    bool receivedEcho = true;
    while (receivedEcho) {
        char message[BUFFER_SIZE];
        int rcvReturnValue = recv(_udpSocket, message, BUFFER_SIZE, 0);
        if (rcvReturnValue == -1) {
            cout << "Error: receive" << endl;
            receivedEcho = false;
            exit(rcvReturnValue);
        } else if (rcvReturnValue == 0) {
            cout << "Error: lost connection!" << endl;
            receivedEcho = false;
            exit(rcvReturnValue);
        } else {
            cout << message << endl;
        }
        return message;
    }
}

int UDPClient::sendMessage(int _udpSocket, const char *_message, int _portNumber, char* _serverIPAddress) {
    int messageSize = strlen(_message) +1;
    sockaddr_in toAddress;
    int toSize = sizeof(toAddress);
    toAddress.sin_family = AF_INET;
    toAddress.sin_port = htons(_portNumber);
    toAddress.sin_addr.s_addr = inet_addr(_serverIPAddress);
    memset(&(toAddress.sin_zero), '\0', 8);

    int sendReturnValue = sendto(_udpSocket, _message, messageSize, 0, (sockaddr*) &toAddress, toSize);
    if (sendReturnValue == -1) {
        cout << "Error: send" << endl;
        exit(sendReturnValue);
    } else {
        cout << "Sent " << sendReturnValue << "Bytes." << endl;
    }

    return sendReturnValue;
}

int UDPClient::closeSocket(int _udpSocket) {
    int closeReturnValue = close(_udpSocket);
    if (closeReturnValue == -1) {
        cout << "Error: close!" << endl;
        exit(closeReturnValue);
    }
}
