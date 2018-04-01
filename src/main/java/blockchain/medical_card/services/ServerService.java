package blockchain.medical_card.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(7373);
		System.out.println("Server started on port 7373.");
		while (true) {
			Socket clientSocket = serverSocket.accept();
			new Thread(new ClientHandler(clientSocket)).start();
		}
	}
}
