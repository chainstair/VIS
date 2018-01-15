//
// Created by David on 08.01.2018.
//

#include <iostream>
#include "UDPClient.h"

using namespace std;

int main(int _argc, char *_argv[]) {

    if (_argc < 3) {
        cout << "Type in portnumber und server IP-address as arguments." << endl;
        return 0;
    }

    int mPortNumber = stoi(_argv[1]);
    char *mServerIPAddress = _argv[2];

    UDPClient *client = new UDPClient();

    int udpSocket = client->initializeSocket();

    bool sendingPossible = true;
    while(sendingPossible){
        char message[BUFFER_SIZE];

        cout << "Send message: ";
        cin.getline(message, BUFFER_SIZE);

        client->sendMessage(udpSocket, message, mPortNumber, mServerIPAddress);

        client->receiveMessage(udpSocket);

        cout<<"-----------------------"<<endl;
        cout<<"Enter q to close the connection, enter c to continue:"<<endl;
        char userDecision;
        cin>>userDecision;

        if (userDecision == 'q'){
            sendingPossible = false;
            break;
        }else if(userDecision == 'c'){
            sendingPossible = true;
        }
        cin.clear();
        cin.ignore();
    }



    client->closeSocket(udpSocket);

    delete client;
}
