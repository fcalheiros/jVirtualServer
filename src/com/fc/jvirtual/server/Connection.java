package com.fc.jvirtual.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Connection implements Cloneable {

	private Listener listener;
	private Socket socketFrom;
	private Socket socketTo;
	private long timeLastActivity;

	public Connection(Listener listener, Socket socketFrom) {
		super();
		this.listener = listener;
		this.socketFrom = socketFrom;
	}

	public void open() throws IOException {
		socketTo = new Socket(listener.getService().getHost(), listener.getService().getPortTo());
		System.out.println(String.format("Service %s connected to the %s:%d.", 
				listener.getService().getName(), listener.getService().getHost(), listener.getService().getPortTo()));
		resetTimer();
		new Thread(new Channel(this, socketFrom.getInputStream(), socketTo.getOutputStream())).start();
		new Thread(new Channel(this, socketTo.getInputStream(), socketFrom.getOutputStream())).start();
		new Thread(this::checkInactifity).start();
	}

	private void checkInactifity() {
		while (true) {
			if (timeLastActivity + listener.getService().getTimeOut() <= new Date().getTime()) {
				try {
					this.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void resetTimer() {
		timeLastActivity = new Date().getTime();
	}

	public boolean isClose() {
		return socketFrom.isClosed() || socketFrom.isClosed();
	}

	public void close() throws IOException {
		socketFrom.close();
		socketTo.close();
		System.out.println(String.format("Service %s close.", listener.getService().getName()));
	}


}
