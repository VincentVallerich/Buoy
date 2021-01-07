package fr.ensisa.gmv.buoys.client.network;

import java.io.OutputStream;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class ClientWriter extends BasicAbstractWriter {

    public ClientWriter(OutputStream outputStream) {
        super(outputStream);
    }

	private void writeNullableString(String text) {
        if (text == null) {
        	writeInt(0);
        } else {
        	writeInt(1);
            writeString(text);
        }
	}

	public void createGetBuoyList(String who) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyLastTick(long id) {
	}

	public void createGetBuoyData(long id, boolean tick, boolean measures) {
	}

}
