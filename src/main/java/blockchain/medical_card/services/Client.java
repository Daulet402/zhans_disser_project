package blockchain.medical_card.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 7373);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Connected to server on port 7373...");

		try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream())) {
			while (true) {
				String userInput = scanner.nextLine();
				outputStreamWriter.write(userInput);
				outputStreamWriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
