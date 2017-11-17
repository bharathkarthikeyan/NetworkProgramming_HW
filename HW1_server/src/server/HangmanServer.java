package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HangmanServer {
	private static ArrayList<String> words;


	public static void main(String[] args) {
		int port = 4444;
		
		//Read port nr from arguments
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.out.println("Usage: java Server [HOSTNAME] [PORTNR (int)]\nWill use standard port " + port);
			}
		}

		readWordList();
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();

				HangmanServer_libraries serverThread = new HangmanServer_libraries(words, clientSocket);
				serverThread.start();
				System.out.println("Connection with a Client established.");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Using buffered Read to read the word list.
	 * */
	public static void readWordList() {
		words = new ArrayList<String>();

		try {
			//Words file is based in the root of the project
			BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
			String line;
			//Read all words from the list and add them to an array
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
