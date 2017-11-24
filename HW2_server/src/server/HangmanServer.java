package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class HangmanServer {
	private static ArrayList<String> words;
	private static String clientChannel = "clientChannel";
	private static String serverChannel = "serverChannel";
	private static String channelType = "channelType";

	public static void main(String[] args) {
		int port = 4444;
		String localhost = "localhost";
		
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
		
		
		try
		{
			ServerSocketChannel channel = ServerSocketChannel.open();	
			ServerSocket serversocket = channel.socket();
			serversocket.bind(new InetSocketAddress(port));
			// marking as non blocking
			channel.configureBlocking(false);
	 		Selector selector = Selector.open();
	 		//channel.register(selector, SelectionKey.OP_ACCEPT);
			SelectionKey socketServerSelectionKey = channel.register(selector,
					SelectionKey.OP_ACCEPT);
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(channelType, serverChannel);
			socketServerSelectionKey.attach(properties);
			
			while(true)
			{ selector.select();
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			
			while(keys.hasNext())
			{
				SelectionKey key = keys.next();
				keys.remove();
			
			if (key.isAcceptable()){
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                SocketChannel channel1 = server.accept();
                System.out.println("Accepted from "+channel1);
                channel1.configureBlocking(false);
                channel1.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                /*SelectionKey clientKey=client.register(
                    selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);*/
								}
			else if (key.isReadable())
			{
				 ServerSocketChannel channel1 = (ServerSocketChannel) key.channel();
				 ByteBuffer buffer = (ByteBuffer) key.attachment();
				 ((SeekableByteChannel) channel1).read(buffer);
			    // ByteBuffer buffer = ByteBuffer.allocate(100);
			    key.interestOps(SelectionKey.OP_WRITE);
            }
			}
			}
		}
			
/*			for (;;) {
				if (selector.select() == 0)
					continue;
				Set(SelectionKey) selectedKeys = selector.selectedKeys();
				Iterator(SelectionKey) iterator = selectedKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (((Map<?, ?>) key.attachment()).get(channelType).equals(
							serverChannel)) {
						ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
								.channel();
						SocketChannel clientSocketChannel = serverSocketChannel
								.accept();
	 
						if (clientSocketChannel != null) {
							// set the client connection to be non blocking
							clientSocketChannel.configureBlocking(false);
							SelectionKey clientKey = clientSocketChannel.register(
									selector, SelectionKey.OP_READ,
									SelectionKey.OP_WRITE);
							Map&lt;String, String&gt; clientproperties = new HashMap&lt;String, String&gt;();
							clientproperties.put(channelType, clientChannel);
							clientKey.attach(clientproperties);
	 
							// write something to the new created client
							CharBuffer buffer = CharBuffer.wrap("Hello client");
							while (buffer.hasRemaining()) {
								clientSocketChannel.write(Charset.defaultCharset()
										.encode(buffer));
							}
							buffer.clear();
						}
				
			}
				}
			}
		}*/
/*		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();

				HangmanServer_libraries serverThread = new HangmanServer_libraries(words, clientSocket);
				serverThread.start();
				System.out.println("Connection with a Client established.");
			}
		} */
		catch (IOException e) {
			e.printStackTrace();
		}
	}

/*	*//**
	 * Using buffered Read to read the word list.
	 * *//*
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
	*/

	/**
	 * Using buffered Read to read the word list.
	 * */
	public static void readWordList() {
		words = new ArrayList<String>();

        String filename = "words.txt";
        try {
            FileInputStream inf = new FileInputStream(filename);
            try (FileChannel channel = inf.getChannel()) {
                MappedByteBuffer buffer
                        = channel.map(FileChannel.MapMode.READ_ONLY,
                                      0, channel.size());
                WritableByteChannel out = Channels.newChannel(System.out);
                while (buffer.hasRemaining()) {
                    out.write(buffer);
                }
            }
        }
        
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
