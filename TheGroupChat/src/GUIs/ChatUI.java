package GUIs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import Client.ChatClientRemoteClass;
import Interfaces.ChatCLientInterface;
import Interfaces.ChatServerInterface;

public class ChatUI {
	// GUI's Components
	JTextArea allMessagestextArea;

	JTextField messageTextField;
	JTextField ipTextField;
	JTextField nameTextField;

	JButton connectButton;
	JButton sendButton;

	JList<String> connectedMembersList;
	JFrame frame;
	JPanel mainPanel;
	JPanel topPanel;
	JPanel bottomPanel;
	JPanel centerPanel;

	// Client-Server classes
	private ChatClientRemoteClass client;
	private ChatServerInterface server;

	public ChatUI() {
		frame = new JFrame("The Group Chat ");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 0, 5, 5));
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout(5, 5));
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(5, 5));

		allMessagestextArea = new JTextArea();
		messageTextField = new JTextField();
		ipTextField = new JTextField();
		nameTextField = new JTextField();

		connectButton = new JButton("Connect");
		sendButton = new JButton("Send");

		connectedMembersList = new JList<String>();

		topPanel.add(new JLabel("Your name: "));
		topPanel.add(nameTextField);
		topPanel.add(new JLabel("Server Address: "));
		topPanel.add(ipTextField);
		topPanel.add(connectButton);

		centerPanel.add(new JScrollPane(allMessagestextArea), BorderLayout.CENTER);
		centerPanel.add(connectedMembersList, BorderLayout.EAST);

		bottomPanel.add(messageTextField, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doConnect();
			}
		});

		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});

		messageTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});
		frame.setContentPane(mainPanel);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void doConnect() {
		if (connectButton.getText().equals("Connect")) {
			if (nameTextField.getText().length() < 2) {
				JOptionPane.showMessageDialog(frame, "You need to type a name");
				return;
			}
			if (ipTextField.getText().length() < 2) {
				JOptionPane.showMessageDialog(frame, "You need to type an IP.");
				return;
			}
			try {
				client = new ChatClientRemoteClass(nameTextField.getText());
				client.setChatGUI(this);
				server =(ChatServerInterface) Naming.lookup("rmi://" + ipTextField.getText() + ":1099/myGroupChatServer");
				server.login(client);
				updateUsers(server.getConnected());
				connectButton.setText("Disconnected");
				//connectButton.setEnabled(false);

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "ERROR! We couldn't connect ....");
			}

		} else {
				try {
					server.logout(client);
				}catch(RemoteException r) {
					r.printStackTrace();
				}
				updateUsers(null);
				connectButton.setText("Connect");
				//connectButton.setEnabled(true);	
		}
	}

	public void sendText() {
		if(connectButton.getText().equals("Connect")) {
			JOptionPane.showMessageDialog(frame, "You need to connect first.");
			return;
		}
		String message = messageTextField.getText();
		message = "[" + nameTextField.getText() + "]: " + message;
		messageTextField.setText("");
		try {
			server.publish(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void writeMessage(String clientMessage) {
		allMessagestextArea.setText(messageTextField.getText() + "\n" + clientMessage);
	}
	
	public void updateUsers(Vector<ChatCLientInterface> clientsVector) {
		DefaultListModel<String> modelList = new DefaultListModel<String>();
		for(ChatCLientInterface client : clientsVector) {
			try {
				String clientName = client.getName();
				modelList.addElement(clientName);
			} catch (RemoteException e) {
				
				e.printStackTrace();
			}
			
		}
		connectedMembersList.setModel(modelList);
	}

	
}
