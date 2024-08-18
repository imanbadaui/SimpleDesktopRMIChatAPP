package Interfaces;

import java.rmi.*;

public interface ChatCLientInterface extends Remote{
	//shows messages from server to the client.
	void tell(String clientMessage) throws RemoteException;
	//sends server name of current client.
	String getName() throws RemoteException;
}
