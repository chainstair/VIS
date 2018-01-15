//
// Created by David on 07.01.2018.
//

#include "TCPClient.h"

TCPClient::TCPClient() {

}

TCPClient::~TCPClient() {

}

int TCPClient::initializeSocket(int _portNumber, char *_serverIPAddress, bool _IPv6) {

   if(_IPv6){
       mClientSocket = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);
   }else {
       mClientSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
   }
    if(mClientSocket == -1){
        cout<<"Error: initialize client socket"<<endl;
        exit(mClientSocket);
    }

    if (_IPv6){
        sockaddr_in6 clientAddr;
        clientAddr.sin6_family = AF_INET6;
        clientAddr.sin6_flowinfo = 0;
        clientAddr.sin6_port = htons(_portNumber);
        clientAddr.sin6_scope_id = 0;

        int addressReturnValue = inet_pton(AF_INET6, "::1", &(clientAddr.sin6_addr));
        if(addressReturnValue==-1){
            cout<<"Error: IPv6 Address"<<endl;
            exit(addressReturnValue);
        }else if(addressReturnValue ==0){
            cout<<"Ip Address could not be converted."<<endl;
            exit(addressReturnValue);
        }

        cout<<"Connecting to Server..."<<endl;

        int connectReturnValue = connect(mClientSocket, (sockaddr*)&clientAddr, sizeof(clientAddr));
        if(connectReturnValue == -1){
            cout<<"Error: connect"<<endl;
            exit(connectReturnValue);
        }else{
            cout<<"Connected"<<endl;
            return mClientSocket;
        }
    }else{
        sockaddr_in clientAddr;
        clientAddr.sin_family = AF_INET;
        clientAddr.sin_port = htons(_portNumber);
        clientAddr.sin_addr.s_addr = inet_addr(_serverIPAddress);
        memset(&(clientAddr.sin_zero), '\0',8);

        cout<<"Connecting to Server..."<<endl;

        int connectReturnValue = connect(mClientSocket, (sockaddr*)&clientAddr, sizeof(clientAddr));
        if(connectReturnValue == -1){
            cout<<"Error: connect"<<endl;
            exit(connectReturnValue);
        }else{
            cout<<"Connected"<<endl;
            return mClientSocket;
        }
    }

}

char *TCPClient::receiveMessage(int _communicationSocket) {

    bool receivedEcho = true;
    while(receivedEcho){
        char message[BUFFER_SIZE];
        int rcvReturnValue = recv(_communicationSocket, message, BUFFER_SIZE, 0);
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

int TCPClient::sendMessage(int _communicationSocket, const char *_message) {

    int messageSize = strlen(_message) +1;
    int sendReturnValue = send(_communicationSocket, _message, messageSize, 0);
    if (sendReturnValue == -1) {
        cout << "Error: send" << endl;
        exit(sendReturnValue);
    } else{
        cout<<"Message successfully sent."<<endl;
        return sendReturnValue;
    }
}

int TCPClient::closeSocket(int _communicationSocket) {
    int closeReturnValue = close(_communicationSocket);
    if (closeReturnValue == -1){
        cout<<"Error: close!"<<endl;
        exit(closeReturnValue);
    }
}
