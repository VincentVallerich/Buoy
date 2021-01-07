package fr.ensisa.gmv.buoys.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.*;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}
	private Buoy buoy;
	private BuoyData buoyData;
	private Version version;
	private long id;

	private void eraseFields() {
		this.buoy = null;
		this.buoyData = null;
		this.version = null;
		this.id = -1;
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		case Protocol.GET_CONFIG_GET_VERSION: readGetVersion(); break;
		case Protocol.GET_CONFIG_CREATE_BUOY: readCreateBuoy(); break;
		case Protocol.GET_SESSION_BUOY_LAST_TICK: readBuoyLastTickId(); break;
		}
	}

	private void readBuoyLastTickId() {
		id = readLong();
	}

	public Version getVersion() {
		return version;
	}

	public Buoy getBuoy() {
		return this.buoy;
	}

	public long getId() {
		return id;
	}

	public BuoyData getBuoyData() {
		return buoyData;
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

	public void readBuoyLastTick() {

	}

	public void readBuoyData() {
		long id = readLong();
		long date = readLong();
		int type = readInt();
		Location location = readLocation();
		State state = readState();
		Battery battery = readBattery();
		Measures measures = readMesures();

		if (type == 0) this.buoyData = new BuoyData(date, id, location, state, battery);
		if (type == 1) this.buoyData = new BuoyData(date, id, location, measures);
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

	public Location readLocation() {
		float longitude = readFloat();
		float latitude = readFloat();
		float altitude = readFloat();

		return new Location(longitude, latitude, altitude);
	}

	public State readState() {
		int state = readInt();
		int detail = readInt();

		return new State(state, detail);
	}

	public Battery readBattery() {
		int level = readInt();
		int temperature = readInt();
		int load = readInt();
		int plug = readInt();
		int discharge = readInt();
		int cycleCount = readInt();

		return new Battery(level, temperature, load, Battery.Plug.values()[plug], discharge, cycleCount);
	}

	public Measures readMesures() {
		Measures measures = new Measures();
		measures.setAcceleration_X(readFloat());
		measures.setAcceleration_Y(readFloat());
		measures.setAcceleration_Z(readFloat());
		measures.setRotation_X(readFloat());
		measures.setRotation_Y(readFloat());
		measures.setRotation_Z(readFloat());
		measures.setNorth(readFloat());
		measures.setTop_temperature(readFloat());
		measures.setTop_humidity(readFloat());
		measures.setTop_light(readFloat());
		measures.setTop_ir(readFloat());
		measures.setBottom_temperature(readFloat());
		measures.setBottom_humidity(readFloat());
		measures.setBottom_light(readFloat());
		measures.setBottom_ir(readFloat());
		measures.setTelemetry_front(readFloat());
		measures.setTelemetry_back(readFloat());
		measures.setTelemetry_left(readFloat());
		measures.setTelemetry_right(readFloat());

		return measures;
	}
}
