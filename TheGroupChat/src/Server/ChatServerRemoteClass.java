package Server;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import Interfaces.ChatCLientInterface;
import Interfaces.ChatServerInterface;

public class ChatServerRemoteClass extends UnicastRemoteObject implements ChatServerInterface{
	private Vector<ChatCLientInterface> clientsVector = new Vector<ChatCLientInterface>() ;
	
	public ChatServerRemoteClass() throws RemoteException {
	}
	
	@Override
	public boolean login(ChatCLientInterface clientRef) throws RemoteException {
		System.out.println(clientRef.getName() + " got connected ....");
		clientRef.tell("You have connected successfully.");
		publish(clientRef.getName() + " has just connected ....");
		clientsVector.add(clientRef);
		return true;
	}
	@Override
	public void publish(String message) throws RemoteException {
		System.out.println(message);
		for(ChatCLientInterface clientRef : clientsVector) {
			try {
				clientRef.tell(message);
			}catch(RemoteException e) {
				e.printStackTrace();
			}	
		}	
	}
	@Override
	public Vector<ChatCLientInterface> getConnected() throws RemoteException {
		
		return clientsVector;
	}
	@Override
	public void logout(ChatCLientInterface clientRef) throws RemoteException {
		System.out.println(clientRef.getName() + " got disconnected ....");
		clientRef.tell("You have disconnected successfully.");
		publish(clientRef.getName() + " has just disconnected ....");
		clientsVector.remove(clientRef);;
		
	}
	
	
}
