package fr.ensisa.gmv.buoys.service.network;

import java.io.InputStream;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class ServiceReader extends BasicAbstractReader {

    public ServiceReader(InputStream inputStream) {
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
