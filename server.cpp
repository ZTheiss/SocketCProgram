#include <iostream>
#include <string>
#include <unistd.h> 
#include <stdio.h> 
#include <sys/socket.h> 
#include <stdlib.h> 
#include <netinet/in.h> 
#define PORT 8080
using namespace std;


int main() {
	int server_fd, new_socket, valread;
	struct sockaddr_in address;
	int opt = 1, addrlen = sizeof(address);
	string buffer;
	string output = "This is a test";

	if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
		perror("socket failed");
		exit(EXIT_FAILURE);
	}

	if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &opt, sizeof(opt))) {
		perror("socket failed");
		exit(EXIT_FAILURE);
	}
}