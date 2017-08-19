/**
 * 
 */
package com.fc.jvirtual.server;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXB;

/**
 * @author felipec
 *
 */
public class VirtualServer {

	public static void main(String... args) {
		try {
			Service service = JAXB.unmarshal(new File(args[0]), Service.class);
			service.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
