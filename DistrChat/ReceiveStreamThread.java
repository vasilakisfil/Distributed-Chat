package DistrChat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vasilakis
 */
public class ReceiveStreamThread implements Runnable {

	GUIClass gui;
	ConnectionsClass connections;

	public ReceiveStreamThread(GUIClass gui, ConnectionsClass connections)
	{
		this.gui=gui;
		this.connections=connections;
	}

	public void run() {
		for( ; ; )
		{
			//gui.appendDBText(gui.getMode() + " entered ReceiveStreamThread");
			if(gui.getMode().equals("server"))
			{
				try {
					gui.appendMAText(connections.serverReadString(),false);
				} catch (IOException ex) {
					Logger.getLogger(ReceiveStreamThread.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if(gui.getMode().equals("client"))
			{
				try {
					gui.appendMAText(connections.clientReadString(),false);
				} catch (IOException ex) {
					Logger.getLogger(ReceiveStreamThread.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
