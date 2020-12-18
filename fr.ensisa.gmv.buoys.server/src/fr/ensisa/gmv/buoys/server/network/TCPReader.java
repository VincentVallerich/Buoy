package fr.ensisa.gmv.buoys.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.Buoy;
import fr.ensisa.gmv.buoys.server.model.Sensors;
import fr.ensisa.gmv.buoys.server.model.Usage;
import fr.ensisa.gmv.buoys.server.model.Version;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}
	private Buoy buoy = new Buoy();
	private Version version = new Version();

	private void eraseFields() {
		this.buoy = null;
		this.version = null;
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		case Protocol.GET_CONFIG_GET_VERSION: readGetVersion();
		case Protocol.GET_CONFIG_CREATE_BUOY: readCreateBuoy();
		}
	}

	public Version getVersion() {
		return version;
	}

	public Buoy getBuoy() {
		return this.buoy;
	}

	private void readCreateBuoy() {
		this.buoy = readBuoy();
	}


	private void readGetVersion() {
		this.version = readVersion();
	}

	private Version readVersion() {
		String number = readString();
		int contentSize = readInt();
		byte[] content = new byte[contentSize];
		for ( int i = 0; i < contentSize; i++)
			content[i] = readByte();

		return new Version(number, content);
	}

	private Buoy readBuoy() {
		Buoy b = new Buoy();
		b.setId(readLong());
		b.setVersion(readString());
		b.setWho(readString());
		b.setUsage(Usage.values()[readInt()]);
		b.setSensors(readSensors());

		return b;
	}

	protected Sensors readSensors(){
		Sensors s = new Sensors();

		s.setSensor3DAcceleration(readBoolean());
		s.setSensor3DRotation(readBoolean());
		s.setSensorNorth(readBoolean());
		s.setSensorTop(readBoolean());
		s.setSensorBottom(readBoolean());
		s.setSensorTelemetry(readBoolean());

		return s;
	}
}
