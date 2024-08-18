package Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

import Interfaces.ChatServerInterface;

public class StartServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ChatServerInterface serverRemoteObject = new ChatServerRemoteClass();
			Naming.rebind("rmi://127.0.0.1/myGroupChatServer", serverRemoteObject);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
