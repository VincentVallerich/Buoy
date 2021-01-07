package fr.ensisa.gmv.buoys.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.Buoy;
import fr.ensisa.gmv.buoys.server.model.BuoyData;
import fr.ensisa.gmv.buoys.server.model.Model;
import fr.ensisa.gmv.buoys.server.model.Version;


public class TCPSession extends Thread {

	private Socket connection;
	private Model model;

	public TCPSession(Socket connection, Model model) {
		this.connection = connection;
		this.model = model;
	}

	public void close () {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		try {
			TCPWriter writer = new TCPWriter (connection.getOutputStream());
			TCPReader reader = new TCPReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType()) {
			case 0 : return false; // socket closed
			case Protocol.GET_CONFIG_GET_VERSION: processGetVersion(reader, writer); break;
			case Protocol.GET_CONFIG_CREATE_BUOY: processCreateBuoy(reader, writer); break;
			case Protocol.GET_SESSION_BUOY_LAST_TICK: processGetBuoyLastTick(reader, writer); break;
			default: return false; // connection jammed
			// to remove before adding anything
			// entry added to remove annoying error reported by compiler
			// case 1:
			}
			writer.send();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void processGetBuoyLastTick(TCPReader reader, TCPWriter writer) {
		long id = reader.getId();
		BuoyData lastTick = model.getBuoyDataTable().getLastTick(id);
		writer.getLastTick(lastTick);
	}

	private void processGetVersion(TCPReader reader, TCPWriter writer) {
		Version version = model.getLastVersion();
		if (version == null) writer.createKO();
		else writer.createGetVersion(version);
	}

	private void processCreateBuoy(TCPReader reader, TCPWriter writer) {
		Buoy buoy = reader.getBuoy();
		if (buoy == null) throw new Error("Buoy cannot be null");
		writer.createCreateBuoy(buoy);
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}
}
