#include <iostream> // cout, cin
#include <sys/socket.h> // socket, bind, listen, accept
#include <netinet/in.h> // IPPROTO_TCP, sockaddr_in, htons/ntohs, INADDR_ANY
#include <unistd.h> // close
#include <arpa/inet.h> // inet_ntop/inet_atop
#include <string.h> // strlen
#include <semaphore.h> // sem_init

#define BUFFER_SIZE 1024

// gewaehrleistet das Laden der entsprechenden Bibliothek
#pragma comment(lib, "Ws2_32.lib")

// Rueckgabewerte
#define SERVER_SOCKET_ERROR 1
#define SERVER_SOCKET_OK 0

// namespace fuer die Verwendung von cin, cout, cerr und endl wird festgelegt
using namespace std;

int main(int _argc, char* _argv[]) {

    int serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (serverSocket == -1){
        cout << "socket init error" << endl;
        return 0;
    }

    sockaddr_in serverAddr;
    serverAddr.sin_addr.s_addr = INADDR_ANY;        //oder inet_addr("134.106.52.222")
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(1);
    memset(&(serverAddr.sin_zero),'\0',8);

    int bindVal = bind(serverSocket, (sockaddr*)&serverAddr, sizeof(serverAddr));
    if (bindVal == -1){
        cout << "bind error" << endl;
        return 0;
    }
    int listVal = listen(serverSocket, SOMAXCONN);
    if (listVal == -1){
        cout << "listen error" << endl;
        return 0;
    }

    sockaddr_in clientAddr;
    int clientAddrSize = sizeof(clientAddr);
    int commSocket = accept(serverSocket, (sockaddr*)&clientAddr, &clientAddrSize);
    if (commSocket == -1) {
        cout << "acception error" << endl;
        return 0;
    }

    //Receive!
    char msg [BUFFER_SIZE];
    int recVal = recv(commSocket, msg, BUFFER_SIZE, 0);
    if (recVal == -1){
        cout << "receiving error" << endl;
        return 0;
    }
    else if (recVal == 0){
        cout << "client connection lost" << endl;
        return 0;
    }
    else {
        cout << msg << endl;
    }

    //Send!
    char* sendMsg = "I bims da Server!\0";
    int sendMsgSize = strlen(sendMsg)+1; //check size!
    int sendVal = send(commSocket, sendMsg, sendMsgSize, 0);
    if (sendVal == -1){
        cout << "sending error" << endl;
        return 0;
    }

}