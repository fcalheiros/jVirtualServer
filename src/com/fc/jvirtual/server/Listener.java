package com.fc.jvirtual.server;

import java.io.IOException;
import java.net.Socket;

public class Listener  implements Runnable {

	private Service service;  
	
	public Listener(Service service) {
		super();
		this.service = service;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println(String.format("Service %s listener in port %d", service.getName(), service.getPortFrom()));
				Socket socketFrom = service.getServerSocket().accept();
				Connection connection = new Connection(this, socketFrom);
				System.out.println(String.format("Service %s connected to the %s:%d.", service.getName(), service.getHost(), service.getPortTo()));
				connection.open();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Service getService() {
		return service;
	}
	
}
