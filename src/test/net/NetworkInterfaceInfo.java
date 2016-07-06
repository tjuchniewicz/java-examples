package test.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkInterfaceInfo {

	public static void main(String[] args) throws SocketException {
		
		for (Enumeration<NetworkInterface> nics = NetworkInterface
				.getNetworkInterfaces(); nics.hasMoreElements();) {
			NetworkInterface ifc = nics.nextElement();
			
			if(!ifc.isUp()) {
				continue;
			}
			
			System.out.println("Found interface: " + ifc.getDisplayName());
			
			for (Enumeration<InetAddress> ias = ifc.getInetAddresses(); ias.hasMoreElements();) {
				
				InetAddress ia = ias.nextElement();
				if (ia instanceof Inet4Address) {
					System.out.println("   Found address: " + ia);	
					System.out.println("      isSiteLocalAddress: " + ia.isSiteLocalAddress());	
					System.out.println("      isLinkLocalAddress: " + ia.isLinkLocalAddress());	
				}
			}
		}
	}
}
