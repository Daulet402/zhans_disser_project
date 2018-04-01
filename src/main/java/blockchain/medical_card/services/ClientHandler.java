package blockchain.medical_card.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHandler implements Runnable {

	private Socket clientSocket;

	@Override
	public void run() {
		System.out.println("running ...");
		if (clientSocket == null)
			throw new IllegalArgumentException("client socket can not be null");

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			Stream<String> lines = bufferedReader.lines();
			lines.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
