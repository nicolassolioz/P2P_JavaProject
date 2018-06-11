package connexion;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class CheckAvailablePort {

	// CODE TAKEN FROM https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
		// ALL RIGHTS RESERVED
		public boolean available(int port) {
		    if (port < 50000 || port > 50099) {
		        throw new IllegalArgumentException("Invalid start port: " + port);
		    }

		    ServerSocket ss = null;
		    DatagramSocket ds = null;
		    try {
		        ss = new ServerSocket(port);
		        ss.setReuseAddress(true);
		        ds = new DatagramSocket(port);
		        ds.setReuseAddress(true);
		        return true;
		    } catch (IOException e) {
		    } finally {
		        if (ds != null) {
		            ds.close();
		        }

		        if (ss != null) {
		            try {
		                ss.close();
		            } catch (IOException e) {
		                /* should not be thrown */
		            }
		        }
		    }

		    return false;
		}
}
