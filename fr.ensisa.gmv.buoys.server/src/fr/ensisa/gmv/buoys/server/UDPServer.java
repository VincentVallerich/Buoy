package fr.ensisa.gmv.buoys.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import fr.ensisa.gmv.buoys.server.model.Model;
import fr.ensisa.gmv.buoys.server.network.UDPSession;
import fr.ensisa.gmv.buoys.network.Protocol;

public class UDPServer extends Thread {

	private DatagramSocket server = null;
	private Model model = null;

	public UDPServer(Model model) {
		super();
		this.model = model;
	}

	public void run () {
		try {
			server = new DatagramSocket(Protocol.BUOYS_UDP_PORT);
			while (true) {
				byte [] buffer = new byte [1500];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				server.receive(packet);
				UDPSession session = new UDPSession (packet, model);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
