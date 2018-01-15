//
// Created by David on 07.01.2018.
//

#include <iostream>
#include "TCPServer.h"

using namespace std;

int main(int _argc, char * _argv[]) {

    bool IPv6 = true;

    if (_argc < 3) {
        cout << "Type in portnumber und Server IP address as arguments." << endl;
        return 0;
    }

    int portNumber = stoi(_argv[1]);
    char *serverIPAddress = _argv[2];

    TCPServer *server = new TCPServer();
    int communicationSocket = server->initializeSocket(portNumber, IPv6);
    if ( communicationSocket == -1) {
        cout<<"Error: initialize socket"<<endl;
        return 0;
    }

    server->closeSocket(communicationSocket);

    delete server;
}




