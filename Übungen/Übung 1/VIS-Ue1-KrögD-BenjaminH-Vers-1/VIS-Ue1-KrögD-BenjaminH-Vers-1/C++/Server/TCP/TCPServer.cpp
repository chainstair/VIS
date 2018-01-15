//
// Created by David on 18.12.2017.
//

#include "TCPServer.h"

using namespace std;

TCPServer::TCPServer() {
}

TCPServer::~TCPServer() {
}

int TCPServer::initializeSocket(int _portNumber, bool _IPv6) {

    if (_IPv6) {
        serverSocket = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);
    } else {
        serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    }

    if (serverSocket == -1) {
        cout << "Error: init" << endl;
        closeSocket(serverSocket);
        return -1;
    }

    if (_IPv6) {
        sockaddr_in6 serverAddr;
        serverAddr.sin6_family = AF_INET6;
        serverAddr.sin6_flowinfo = 0;
        serverAddr.sin6_port = htons(_portNumber);
        serverAddr.sin6_scope_id = 0;
        serverAddr.sin6_addr = IN6ADDR_ANY_INIT;

        int bindingReturnValue = bind(serverSocket, (sockaddr *) &serverAddr, sizeof(serverAddr));
        if (bindingReturnValue == -1) {
            cout << "Error: bind" << endl;
            return -1;
        }

    } else {
        sockaddr_in serverAddr;
        serverAddr.sin_family = AF_INET;
        serverAddr.sin_port = htons(_portNumber);
        serverAddr.sin_addr.s_addr = INADDR_ANY;
        memset(&(serverAddr.sin_zero), '\0', 8);

        int bindingReturnValue = bind(serverSocket, (sockaddr *) &serverAddr, sizeof(serverAddr));
        if (bindingReturnValue == -1) {
            cout << "Error: bind" << endl;
            closeSocket(serverSocket);
            return -1;
        }

    }


    int bOptVal = 1;
    int bOptLen = sizeof(bOptVal);
    int setSocketOptionsReturnValue = setsockopt(serverSocket, SOL_SOCKET, SO_REUSEADDR, (char *) &bOptVal, bOptLen);
    if (setSocketOptionsReturnValue == -1) {
        cout << "Error: SetSocketOptions" << endl;
        closeSocket(serverSocket);
        return -1;
    }

    int listenReturnValue = listen(serverSocket, SOMAXCONN);
    if (listenReturnValue == -1) {
        cout << "Error: listen" << endl;
        closeSocket(serverSocket);
        return -1;
    }

    cout << "Waiting for client..." << endl;

    while (true) {

        if (_IPv6) {
            sockaddr_in6 clientAddr;
            int clientAddrSize = sizeof(clientAddr);
            int commSocket = accept(serverSocket, (sockaddr *) &clientAddr, &clientAddrSize);
            if (commSocket == -1) {
                cout << "Error: accept" << endl;
                closeSocket(serverSocket);
                return -1;
            } else {
                cout << "Client accepted" << endl;
                mCommunicationSocket = commSocket;
                receiveMessage(commSocket, BUFFER_SIZE);
            }
        } else {
            sockaddr_in clientAddr;
            int clientAddrSize = sizeof(clientAddr);
            int commSocket = accept(serverSocket, (sockaddr *) &clientAddr, &clientAddrSize);
            if (commSocket == -1) {
                cout << "Error: accept" << endl;
                closeSocket(serverSocket);
                return -1;
            } else {
                cout << "Client accepted" << endl;
                cout << "Client address: " << inet_ntoa(clientAddr.sin_addr) << endl;
                cout << "Communication port: " << clientAddr.sin_port << endl;
                mCommunicationSocket = commSocket;
                receiveMessage(commSocket, BUFFER_SIZE);
            }
        }


    }
}

char *TCPServer::receiveMessage(int _communicationSocket, int _bufferSize) {
    bool clientIsConnected = true;
    char message[BUFFER_SIZE];
    string echoMessageString;

    while (clientIsConnected) {
        echoMessageString = "";

        AirSensor* airSensor = new AirSensor();
        LightSensor* lightSensor = new LightSensor();
        NoiseSensor* noiseSensor = new NoiseSensor();

        int rcvReturnValue = recv(_communicationSocket, message, _bufferSize, 0);
        if (rcvReturnValue == -1) {
            cout << "Error: receive" << endl;
            clientIsConnected = false;
        } else if (rcvReturnValue == 0) {
            cout << "Client disconnected!" << endl;
            clientIsConnected = false;
            return (char *) rcvReturnValue;
        } else if (strcmp(message, (char *) "sensortypes#") == 0) {
            echoMessageString.append(lightSensor->getName());
            echoMessageString.append(";");
            echoMessageString.append(noiseSensor->getName());
            echoMessageString.append(";");
            echoMessageString.append(airSensor->getName());
            echoMessageString.append("#");
        }else if(strcmp(message, (char *) "sensor;light#") == 0){
            echoMessageString.append("12345678");
            echoMessageString.append("|");
            echoMessageString.append(lightSensor->toString());
            echoMessageString.append("#");
        }else if(strcmp(message, (char *) "sensor;noise#") == 0){
            echoMessageString.append("12345678");
            echoMessageString.append("|");
            echoMessageString.append(noiseSensor->toString());
            echoMessageString.append("#");
        }else if(strcmp(message, (char *) "sensor;air#") == 0){
            echoMessageString.append("12345678");
            echoMessageString.append("|");
            echoMessageString.append(airSensor->toString());
            echoMessageString.append("#");
        }else if(strcmp(message, (char *) "sensor;ALL#") == 0){
            echoMessageString.append("12345678");
            echoMessageString.append("|");
            echoMessageString.append(airSensor->getName());
            echoMessageString.append(";");
            echoMessageString.append(airSensor->toString());
            echoMessageString.append("|");
            echoMessageString.append(noiseSensor->getName());
            echoMessageString.append(";");
            echoMessageString.append(noiseSensor->toString());
            echoMessageString.append("|");
            echoMessageString.append(lightSensor->getName());
            echoMessageString.append(";");
            echoMessageString.append(lightSensor->toString());
            echoMessageString.append("#");
        } else {
            cout << "Received " << rcvReturnValue << " Bytes." << endl;
            string echo = "ECHO: ";
            echoMessageString = echo + message;
        }
        const char *echoMessage = echoMessageString.c_str();
        int messageSize = strlen(echoMessage) + 1;
        sendMessage(mCommunicationSocket, echoMessage);
    }
    return message;
}

int TCPServer::sendMessage(int _communicationSocket, const char *_message) {
    int sendMessageSize = strlen(_message) + 1;
    int sendReturnValue = send(_communicationSocket, _message, sendMessageSize, 0);
    if (sendReturnValue == -1) {
        cout << "Error: send" << endl;
        exit(sendReturnValue);
    } else {
        cout << "Sent " << sendReturnValue << "Bytes." << endl;
    }
    return sendReturnValue;
}

int TCPServer::closeSocket(int _communicationSocket) {
    int closeReturnValue = close(_communicationSocket);
    if (closeReturnValue == -1) {
        cout << "Error: close!" << endl;
        return closeReturnValue;
    }
}











