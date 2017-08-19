/**
 * 
 */
package com.fc.jvirtual.server;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXB;

/**
 * @author felipec
 *
 */
public class VirtualServer {



	public static void main(String... args) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("/home/felipec"), "*.xml")) {
			stream.forEach((file) -> {
				JAXB.unmarshal(file.toFile(), Service.class).init();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
