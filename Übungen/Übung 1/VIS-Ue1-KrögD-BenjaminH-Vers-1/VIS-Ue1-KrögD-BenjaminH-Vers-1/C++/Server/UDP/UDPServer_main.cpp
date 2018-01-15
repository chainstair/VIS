//
// Created by David on 08.01.2018.
//

#include <iostream>
#include "UDPServer.h"

using namespace std;

int main(int _argc, char * _argv[]) {

    if (_argc < 3) {
        cout << "Type in portnumber und Server IP address as arguments." << endl;
        return 0;
    }

    int portNumber = stoi(_argv[1]);
    char *serverIPAddress = _argv[2];

    UDPServer *server = new UDPServer();
    int udpSocket = server->initializeSocket(portNumber);
    if ( udpSocket == -1) {
        cout<<"Error: initialize socket"<<endl;
        return 0;
    }

    server->receiveMessage(udpSocket);

    server->closeSocket(udpSocket);

    delete server;
}