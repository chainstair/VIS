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

    //ClientSocket
    int clientSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    sockaddr_in clientAddr;
    clientAddr.sin_addr.s_addr = INADDR_ANY;        //oder inet_addr("134.106.52.222")
    clientAddr.sin_family = AF_INET;
    clientAddr.sin_port = htons(1);
    memset(&(clientAddr.sin_zero),'\0',8);

    //ServerSocket
    int serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    sockaddr_in serverAddr;
    serverAddr.sin_addr.s_addr = INADDR_ANY;        //oder inet_addr("134.106.52.222")
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(1);
    memset(&(serverAddr.sin_zero),'\0',8);

    if (serverSocket == -1){
        cout << "server socket error" << endl;
        return 0;
    }

    int connVal = connect(serverSocket, (sockaddr*)&serverAddr, sizeof(serverAddr));
    if (connVal == -1){
        cout << "connection error" << endl;
        return 0;
    }

    //Send!
    char* sendMsg = "Hello World!\0";
    int sendMsgSize = strlen(sendMsg)+1; //check size!
    int sendVal = send(serverSocket, sendMsg, sendMsgSize, 0);
    if (sendVal == -1){
        cout << "sending error" << endl;
        return 0;
    }

    //Receive!
    char msg [BUFFER_SIZE];
    int recVal = recv(serverSocket, msg, BUFFER_SIZE, 0);
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

    //CLOSE!
    int closeVal = close(serverSocket);
    if (closeVal == -1) {
        cout << "closing error" << endl;
        return 0;
    }
}