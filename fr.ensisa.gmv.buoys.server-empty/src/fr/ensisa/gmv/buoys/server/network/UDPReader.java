package fr.ensisa.gmv.buoys.server.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import fr.ensisa.gmv.buoys.server.model.BuoyData;
import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class UDPReader extends BasicAbstractReader {

	private BuoyData data;

	public UDPReader(byte [] data) {
		super (new ByteArrayInputStream(data));
	}

	private int readAsByte() {
        return (int) (readByte() & 0xFF);
	}

	private BuoyData readUDPStd1() {
		return null;
	}

	private BuoyData readUDPStd2() {
		return null;
	}

	private BuoyData readUDPService() {
		return null;
	}

	public void receive() throws IOException {
		type = readInt ();
		switch (type) {
		case 0 : break;
		case Protocol.UDP_STD1: 	data = readUDPStd1(); break;
		case Protocol.UDP_STD2:		data = readUDPStd2(); break;
		case Protocol.UDP_SERVICE:	data = readUDPService (); break;
		}
	}

	public BuoyData getBuoyData() {
		return data;
	}

}
