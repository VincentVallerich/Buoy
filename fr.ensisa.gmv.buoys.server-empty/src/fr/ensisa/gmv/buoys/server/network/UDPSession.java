package fr.ensisa.gmv.buoys.server.network;

import java.io.IOException;
import java.net.DatagramPacket;

import fr.ensisa.gmv.buoys.server.model.BuoyData;
import fr.ensisa.gmv.buoys.server.model.Model;
import fr.ensisa.gmv.buoys.network.Protocol;

public class UDPSession extends Thread {

	private DatagramPacket packet;
	private Model model = null;

	public UDPSession(DatagramPacket packet, Model model) {
		this.packet = packet;
		this.model = model;
	}

	public void close () {
		this.interrupt();
		packet = null;
	}

	private void processUDPStd1(UDPReader reader) {
		BuoyData tick = reader.getBuoyData();
		model.getBuoyDataTable().add(tick);
	}

	private void processUDPStd2(UDPReader reader) {
		BuoyData tick = reader.getBuoyData();
		model.getBuoyDataTable().add(tick);
	}

	private void processUDPService(UDPReader reader) {
		BuoyData tick = reader.getBuoyData();
		model.getBuoyDataTable().add(tick);
	}

	public void operate() {
		try {
			UDPReader reader = new UDPReader (packet.getData());
			reader.receive ();
			switch (reader.getType ()) {
			case Protocol.UDP_STD1		: processUDPStd1 (reader); break;
			case Protocol.UDP_STD2		: processUDPStd2 (reader); break;
			case Protocol.UDP_SERVICE	: processUDPService(reader); break;
			}
		} catch (IOException e) {
		}
	}

	public void run() {
		operate();
		packet = null;
	}

}
