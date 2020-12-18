package fr.ensisa.gmv.buoys.server.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.Buoy;
import fr.ensisa.gmv.buoys.server.model.Sensors;
import fr.ensisa.gmv.buoys.server.model.Usage;
import fr.ensisa.gmv.buoys.server.model.Version;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class TCPWriter extends BasicAbstractWriter {

    public TCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }

    public void createGetVersion(Version version) {
        if (version == null) throw new Error("Version cannot be null");
        writeNullableString(version.getNumber());
        writeInt(version.getContent().length);
        for (byte b : version.getContent())
            writeByte(b);
    }

    public void createCreateBuoy(Buoy buoy) {
        if (buoy == null) throw new Error("Buoy cannot be null");
        writeBuoy(buoy);
    }

    private void writeBuoy(Buoy buoy){
        writeLong(buoy.getId());
        writeNullableString(buoy.getVersion());
        writeNullableString(buoy.getWho());
        writeInt(buoy.getUsage().ordinal());
        writeSensor(buoy.getSensors());
    }

    private void writeSensor(Sensors s){
        writeBoolean(s.isSensor3DAcceleration());
        writeBoolean(s.isSensor3DRotation());
        writeBoolean(s.isSensorNorth());
        writeBoolean(s.isSensorTop());
        writeBoolean(s.isSensorBottom());
        writeBoolean(s.isSensorTelemetry());
    }

    private void writeNullableString(String s){
        if (s == null) {
            writeString("null");
        } else {
            writeString(s);
        }
    }
}
