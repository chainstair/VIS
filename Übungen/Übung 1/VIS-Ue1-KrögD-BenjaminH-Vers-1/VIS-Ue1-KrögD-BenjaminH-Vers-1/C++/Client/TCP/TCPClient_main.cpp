//
// Created by David on 07.01.2018.
//

#include <iostream>
#include "TCPClient.h"

using namespace std;

int main(int _argc, char * _argv[]) {

    bool IPv6 = true;

    if (_argc < 3) {
        cout << "Type in portnumber and server IP-address as arguments." << endl;
        return 0;
    }

    int portNumber = stoi(_argv[1]);
    char* serverIPAddress = _argv[2];
    char* protocol = _argv[3];

    TCPClient *client = new TCPClient();
    
    int communicationSocket = client->initializeSocket(portNumber, serverIPAddress, IPv6);

    bool isConnected = true;
    while(isConnected){
        char message[BUFFER_SIZE];

        cout<<"Send message: ";
        cin.getline(message,BUFFER_SIZE);

        client->sendMessage(communicationSocket, message);

        client->receiveMessage(communicationSocket);

        cout<<"-----------------------"<<endl;
        cout<<"Enter q to close the connection, enter c to continue:"<<endl;
        char userDecision;
        cin>>userDecision;

        if (userDecision == 'q'){
            isConnected = false;
            break;
        }else if(userDecision == 'c'){
            isConnected = true;
        }
        cin.clear();
        cin.ignore();
    }

    client->closeSocket(communicationSocket);

    delete client;
}
