package fr.ensisa.gmv.buoys.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
			case Protocol.GET_CONFIG_GET_VERSION: 
		}
	}

}
