package DistrChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import org.teleal.cling.*;
import org.teleal.cling.support.model.*;
import org.teleal.cling.support.igd.PortMappingListener;


public class ConnectionsClass {

    private String ip,Nickname;
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
		this.Nickname="";

    }
	public void setNickname(String Nickname)
	{
		this.Nickname=Nickname;
	}
	public String getNickname()
	{
		return this.Nickname;
	}
	public String getRNickname()
	{
		if(!this.Nickname.equals(""))
		{
			return "<"+this.Nickname+">";
		}
		else return "";
	}
	public void setIp(String ip)
	{
		this.ip=ip;
	}
	public String clientConnect(String ip) throws IOException
	{
		this.setIp(ip);
		gui.appendDBText("connecting to " + ip);
		try {
		clientSocket = new Socket(ip,35000);
		} catch (UnknownHostException e) {
			return "Don't know about host:" + ip ;
		} catch (IOException e) {
			return "Couldn't get I/O for the connection to: " + ip;
		}
		clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
		clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
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
            serverSocket = new ServerSocket(35000);
			gui.appendDBText("socket opened");
        } catch (IOException e) {
            gui.appendDBText("Could not listen on port:");
        }

		PortMapping desiredMapping = new PortMapping(35000,"192.168.0.3",PortMapping.Protocol.TCP,"My Port Mapping");
		UpnpService upnpService = new UpnpServiceImpl(new PortMappingListener(desiredMapping));
		upnpService.getControlPoint().search();
		
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
		//gui.appendDBText("inside clientSendString function");
		clientOut.println(this.getRNickname()+string);
	}

	public String clientReadString() throws IOException
	{
		//gui.appendDBText("inside clientReadString function");
		return clientIn.readLine();
	}
	public void serverSendString(String string)
	{
		//gui.appendDBText("inside serverSendString function");
		serverOut.println(this.getRNickname()+string);
	}
	public String serverReadString() throws IOException
	{
		//gui.appendDBText("inside serverReadString function");
		return serverIn.readLine();
	}

}
