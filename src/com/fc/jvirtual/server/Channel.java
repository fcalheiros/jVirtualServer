package com.fc.jvirtual.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Channel implements Runnable {
	
	private final InputStream input;
	private final OutputStream output;
	private final Connection connection;
	
	public Channel(Connection connection, InputStream input, OutputStream output) {
		this.connection = connection;
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		try {	
			while (!connection.isClose()) {
				byte[] b = new byte[input.available()];
				if (b.length > 0) {
					input.read(b);
					output.write(b);
					output.flush();
					new Thread(connection::resetTimer).start();;
				} else {
					TimeUnit.MILLISECONDS.sleep(10);
				}
			}
			input.close();
			output.close();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
