package com.fc.jvirtual.server;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;


public class VirtualServerTest {

	@Test
	public void testVirtualServer() throws Exception {
		
		// Starting the virtual server
		VirtualServer.main("./test");
		
		// Creating server
		Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(9992);
				try {
					Socket socket = serverSocket.accept();
					DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					return inputStream.readUTF();
				} finally {
					serverSocket.close();	
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		// Creating a client
		Socket socketClient = new Socket("127.0.0.1", 9991);
		DataOutputStream dataOutputStream = new DataOutputStream(socketClient.getOutputStream());
		String clientMessage = "It's a test message";
		dataOutputStream.writeUTF(clientMessage);
		dataOutputStream.flush();
		dataOutputStream.close();
		socketClient.close();
		
		String serverMessage = future.get();
		
		System.out.println(String.format("Mesagem client \"%s\" and mesagem server \"%s\".", clientMessage, serverMessage));
		assertEquals(clientMessage, serverMessage);
	}

}
