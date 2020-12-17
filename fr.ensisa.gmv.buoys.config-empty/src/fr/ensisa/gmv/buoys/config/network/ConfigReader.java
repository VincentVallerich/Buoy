package fr.ensisa.gmv.buoys.config.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class ConfigReader extends BasicAbstractReader {

    public ConfigReader(InputStream inputStream) {
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
