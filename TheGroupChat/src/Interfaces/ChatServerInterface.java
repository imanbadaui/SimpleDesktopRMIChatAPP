package Interfaces;

import java.rmi.*;
import java.util.*;

public interface ChatServerInterface extends Remote{
	//Register client to server.
	boolean login(ChatCLientInterface clientRef) throws RemoteException;
	//publish message to all clients.
	void publish(String message)  throws RemoteException;
	//get a list of all connected clients.
	Vector<ChatCLientInterface> getConnected()  throws RemoteException;
	//unregister client from server.
	void logout(ChatCLientInterface clientRef) throws RemoteException;
	
}
