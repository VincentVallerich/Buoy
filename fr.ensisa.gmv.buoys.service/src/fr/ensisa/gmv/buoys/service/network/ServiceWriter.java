package fr.ensisa.gmv.buoys.service.network;

import java.io.OutputStream;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class ServiceWriter extends BasicAbstractWriter {

    public ServiceWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createGetBuoyList(String who) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyLastTick(long id) {
		writeInt(Protocol.GET_SESSION_BUOY_LAST_TICK);
		writeLong(id);
	}

}
