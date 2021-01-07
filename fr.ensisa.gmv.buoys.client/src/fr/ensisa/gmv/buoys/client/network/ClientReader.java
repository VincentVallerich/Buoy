package fr.ensisa.gmv.buoys.client.network;

import java.io.InputStream;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class ClientReader extends BasicAbstractReader {

    public ClientReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
    }

	public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_KO:
        	break;
        }
    }

}
