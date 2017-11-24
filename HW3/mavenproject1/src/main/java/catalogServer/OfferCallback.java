package catalogServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OfferCallback extends Remote
{

	public void notify(Offer offer, Offer matchingOffer, String message) throws RemoteException;


	public String getClientId() throws RemoteException;

}
