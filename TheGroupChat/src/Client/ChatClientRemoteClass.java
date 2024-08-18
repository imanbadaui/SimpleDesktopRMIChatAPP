package Client;

import java.rmi.RemoteException;
import java.rmi.server.*;

import GUIs.ChatUI;
import Interfaces.ChatCLientInterface;

public class ChatClientRemoteClass extends UnicastRemoteObject implements ChatCLientInterface{
	private String clientName;
	private ChatUI chatUI;
	
	public ChatClientRemoteClass(String clientName) throws RemoteException {
		this.clientName = clientName;
	}
	
	@Override
	public void tell(String clientMessage) throws RemoteException {
		System.out.println(clientMessage);
		chatUI.writeMessage(clientMessage);
		
	}
	@Override
	public String getName()  throws RemoteException {
		return clientName;
	}
	
	public void setChatGUI(ChatUI chatUI) {
		this.chatUI = chatUI;
	}
	
}
