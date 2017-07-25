package com.font;

import org.eclipse.jetty.server.Server;

public class Util {
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(Integer.parseInt(PropertiesUtil.getPropMap().get("port")));
        server.setHandler(new FontHandler());
        server.start();
        server.join();
	}
	
}
