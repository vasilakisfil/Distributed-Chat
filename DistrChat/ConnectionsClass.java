package DistrChat;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vasilakis
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class ConnectionsClass {

    private String ip;
	Socket clientSocket = null;
	PrintWriter clientOut = null;
	BufferedReader clientIn = null;

	Socket serverEchoSocket = null;
	PrintWriter serverOut = null;
	BufferedReader serverIn = null;

	GUIClass gui;


    public ConnectionsClass(GUIClass gui)
    {
		this.gui=gui;

    }
	
	public void setIp(String ip)
	{
		this.ip=ip;
	}
	public String clientConnect(String ip)
	{
		this.setIp(ip);
		try {
		clientSocket = new Socket("127.0.0.1",15000);
		clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
		clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			return "Don't know about host: mmmm.";
		} catch (IOException e) {
			return "Couldn't get I/O for the connection to: mplampla.";
		}
		
		Thread receiveStream = new Thread(new ReceiveStreamThread(gui, this));
		receiveStream.start();
		gui.enableInputAreas(true);
		return "connected to ip: " + ip;
	}

	public void serverListen() throws IOException
	{

		gui.appendDBText("trying to open the socket");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(15000);
			gui.appendDBText("socket opened\n");
        } catch (IOException e) {
            gui.appendDBText("Could not listen on port: 4444");
        }

		Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
			gui.appendDBText("Client connected");
        } catch (IOException e) {
            gui.appendDBText("Accept failed");
        }

		gui.enableInputAreas(true);
        serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
        serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		Thread receiveStream = new Thread(new ReceiveStreamThread(gui, this));
		receiveStream.start();
	}

	public void clientSendString(String string)
	{
		gui.appendDBText("inside clientSendString function");
		clientOut.println(string);
	}

	public String clientReadString() throws IOException
	{
		gui.appendDBText("inside clientReadString function");
		return clientIn.readLine();
	}
	public void serverSendString(String string)
	{
		gui.appendDBText("inside serverSendString function");
		serverOut.println(string);
	}
	public String serverReadString() throws IOException
	{
		gui.appendDBText("inside serverReadString function");
		return serverIn.readLine();
	}

}
