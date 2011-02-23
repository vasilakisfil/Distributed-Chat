/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DistrChat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vasilakis
 */
public class ServerListenThread implements Runnable {

	GUIClass gui;
	ConnectionsClass connections;

	public ServerListenThread(GUIClass gui, ConnectionsClass connections)
	{
		this.gui=gui;
		this.connections=connections;
	}

	public void run() {
		try {
			connections.serverListen();
		} catch (IOException ex) {
			Logger.getLogger(ServerListenThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


}
