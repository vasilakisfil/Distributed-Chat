package DistrChat;


// Demonstrating GridBagLayout.
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class GUIClass extends JFrame implements KeyListener, ActionListener
{
	//global variables
	private static final long serialVersionUID = 1L;
	private String mode="client";
	private GridBagLayout layout; // layout of this frame
	JButton enter,beServer;
	JTextField textFieldIP,userInputField;
	JLabel  label;
	JTextArea dBtextArea,messageArea;
	//declarations of important classes
	ConnectionsClass connections;
	// set up GUI
	public GUIClass()
	{
		//GUI variables and declarations
		super( "GridBagLayout" );
		layout = new GridBagLayout();
		setLayout( layout ); // set frame layout
		JScrollPane barText; // Scroll pane for text area
		Dimension taDim = new Dimension();
		taDim.height=10;
		taDim.width=1000;

		GridBagConstraints c1 = new GridBagConstraints();
		GridBagConstraints c2 = new GridBagConstraints();
		GridBagConstraints c3 = new GridBagConstraints();
		GridBagConstraints c4 = new GridBagConstraints();
		GridBagConstraints bS = new GridBagConstraints();
		GridBagConstraints mA = new GridBagConstraints();
		GridBagConstraints uI = new GridBagConstraints();




		//creating the GUI
		label = new JLabel("Enter IP: ");
		c1.fill = GridBagConstraints.NONE;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of space
		c1.insets = new Insets(10,10,0,0);  //top padding
		c1.weighty = 0.01;
		this.add(label, c1);

		textFieldIP = new JTextField();
		//c2.fill = GridBagConstraints.HORIZONTAL;
		c2.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of space
		c2.insets = new Insets(10,0,0,0);  //top padding
		c2.gridx = 1;
		c2.gridy = 0;
		c2.ipadx=150;
		this.add(textFieldIP, c2);
		

		enter = new JButton("Enter");
		c3.fill = GridBagConstraints.NONE;
		c3.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of spac
		c3.insets = new Insets(7,5,0,0);  //top padding
		c3.gridx = 2;
		c3.gridy = 0;
		this.add(enter, c3);
		enter.addActionListener(this);


		beServer = new JButton("server mode");
		bS.fill = GridBagConstraints.NONE;
		bS.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of spac
		bS.insets = new Insets(7,10,0,0);  //top padding
		bS.weightx = 0.1;
		bS.gridx = 3;
		bS.gridy = 0;
		this.add(beServer, bS);
		beServer.addActionListener(this);

		messageArea = new JTextArea(10,40);
		messageArea.setLineWrap(true);
		messageArea.setEditable(false);
		messageArea.setBackground(Color.gray);
		JScrollPane maScroller = new JScrollPane( messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		mA.fill = GridBagConstraints.BOTH;
		mA.anchor = GridBagConstraints.CENTER;
		mA.insets = new Insets(0,10,10,10);  //top padding
		mA.gridwidth = 4;
		mA.weighty=0.1;
		mA.gridx=0;
		mA.gridy=1;
		mA.ipadx=40;
		mA.ipady=40;
		this.add(maScroller,mA);

		userInputField = new JTextField();
		uI.fill = GridBagConstraints.HORIZONTAL;
		uI.anchor = GridBagConstraints.CENTER;
		uI.insets = new Insets(0,10,10,10);  //top padding
		uI.gridwidth = 4;
		//uI.weighty=0.01;
		uI.gridx=0;
		uI.gridy=2;
		uI.ipadx=40;
		uI.ipady=0;
		userInputField.addKeyListener(this);
		this.add(userInputField,uI);


		dBtextArea = new JTextArea(2,2);
		dBtextArea.setLineWrap(true);
		dBtextArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Debugging Area"),
							BorderFactory.createEmptyBorder(5,5,5,5)));
		dBtextArea.setEditable(false);
		dBtextArea.setRows(1);
		//bar = new JScrollPane( textArea );
		//sbrText = new JScrollPane(textArea);
		JScrollPane dbScroller = new JScrollPane( dBtextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		//BorderFactory.


		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.anchor = GridBagConstraints.PAGE_END;
		c4.insets = new Insets(0,10,10,10);  //top padding
		c4.gridwidth = 4;
		c4.gridx=0;
		c4.gridy=3;
		c4.ipadx=40;
		c4.ipady=20;
		this.add(dbScroller,c4);

		connections = new ConnectionsClass(this);
		this.userInputField.setEditable(false);
		this.messageArea.setEditable(false);

	} // end GridBagFrame constructor
	public void enableInputAreas(boolean b)
	{
		this.userInputField.setEditable(b);
		this.messageArea.setEditable(b);
		if(b==true)	this.messageArea.setBackground(Color.white);
		else messageArea.setBackground(Color.gray);
	}
	public void appendMAText(String text)
	{
		messageArea.append(text.concat(System.getProperty("line.separator")));
	}
	public void appendDBText(String text)
	{
		dBtextArea.append(text.concat(System.getProperty("line.separator")));
	}
	private void setMode(String string)
	{
		if(string.equals("server")||string.equals("client"))
		{
			this.mode=string;
		}
		else this.mode="client";
	}
	public String getMode()
	{
		return mode;
	}

	//event handling
    public void actionPerformed(ActionEvent e) {
		//connecting to a server
        if(e.getActionCommand().equals("Enter"))
        {
            this.appendDBText("connecting to "+"\n");
			textFieldIP.setEditable(false);
			this.appendDBText(connections.clientConnect(textFieldIP.getText()));
			
        }
		//waiting for a client
		else if(e.getActionCommand().equals("server mode"))
		{
			beServer.setText("client mode");
			textFieldIP.setEditable(false);
			enter.setEnabled(false);
			this.setMode("server");
			this.appendDBText("Transforming to server mode");
			this.appendDBText("Server listens fo new incoming connections...");
			
			Thread serverListen = new Thread(new ServerListenThread(this,connections));
			serverListen.start();

			
		}
		else if(e.getActionCommand().equals("client mode"))
		{
			textFieldIP.setEditable(true);
			enter.setEnabled(true);
			beServer.setText("server mode");
			this.appendDBText("client mode");

		}
    }

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
	//displayInfo(e, "KEY TYPED: ");
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
		{
			String temp = userInputField.getText();
			String eol = System.getProperty ("line.separator");
			//temp=temp.concat(eol);
			if(this.getMode().equals("server"))
			{
				connections.serverSendString(temp);
			}
			else if(this.getMode().equals("client"))
			{
				connections.clientSendString(temp);
			}
			this.appendMAText(temp);
			userInputField.setText("");
		}
	//displayInfo(e, "KEY PRESSED: ");
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
	//displayInfo(e, "KEY RELEASED: ");
    }

    private void displayInfo(KeyEvent e, String keyStatus){

        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }

        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }

        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }

        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
		System.out.printf("%s\n\n%s\n\n%s\n\n\n\n", keyString, actionString, locationString);


    }

} // end class GridBagFrame