package com.fc.jvirtual.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.stream.IntStream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class Service {
	
	private String name;
	private String host;
	private Integer portFrom;
	private Integer portTo;
	private Integer nListeners;
	private Long timeOut;

	
	private ServerSocket serverSocket;
	
	public void init() {
		try (ServerSocket serverSocket = new ServerSocket(portFrom);) {
			this.serverSocket = serverSocket;
			System.out.println(String.format("Start service %s.", name));
			IntStream.range(0, nListeners).forEach(i -> (new Thread(new Listener(this))).start());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	@XmlElement
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPortFrom() {
		return portFrom;
	}
	@XmlElement
	public void setPortFrom(Integer portFrom) {
		this.portFrom = portFrom;
	}
	public Integer getPortTo() {
		return portTo;
	}
	@XmlElement
	public void setPortTo(Integer portTo) {
		this.portTo = portTo;
	}
	public Integer getnListeners() {
		return nListeners;
	}
	@XmlElement
	public void setnListeners(Integer nListeners) {
		this.nListeners = nListeners;
	}
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	@XmlElement
	public Long getTimeOut() {
		return timeOut;
	}
}
