package fr.ensisa.gmv.buoys.server.network;

import java.io.OutputStream;
import java.util.Collection;
import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.Version;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class TCPWriter extends BasicAbstractWriter {

    public TCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }

    public void createGetVersion(Version version) { writeString(version.getNumber()); }
}
