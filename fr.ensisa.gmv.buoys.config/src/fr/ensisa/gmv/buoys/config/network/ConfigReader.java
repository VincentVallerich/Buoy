package fr.ensisa.gmv.buoys.config.network;

import java.io.InputStream;

import fr.ensisa.gmv.buoys.config.model.Buoy;
import fr.ensisa.gmv.buoys.config.model.Sensors;
import fr.ensisa.gmv.buoys.config.model.Usage;
import fr.ensisa.gmv.buoys.config.model.Version;
import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class ConfigReader extends BasicAbstractReader {

    public ConfigReader(InputStream inputStream) {
        super(inputStream);
    }

    Buoy buoy;
    Version version;
    int type;

    private void eraseFields() {
        this.buoy = null;
        this.version = null;
        this.type = -1;
    }

    public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.GET_CONFIG_GET_VERSION: readGetCurrentVersion(); break;
        case Protocol.GET_CONFIG_CREATE_BUOY: readCreateBuoy(); break;
        case Protocol.GET_CONFIG_UPDATE_BUOY: readUpdateBuoy(); break;
        case Protocol.GET_CONFIG_GET_BUOYLIST: getBuoyList(); break;
        case Protocol.REPLY_KO: break;
        }
    }

    public List<Buoy> getBuoyList() {
        for (int i = 0; i < size; i++)
            bb.add(getCreateBuoy());
        return bb;
    }

    private void readGetCurrentVersion() {
        this.version = getCurrentVersion();
    }

    private void readCreateBuoy() {
        this.buoy = getCreateBuoy();
    }

    private void readUpdateBuoy() {
        this.buoy = getUpdateBuoy();
    }
    public int getType() {
        return type;
    }

    public Buoy getBuoy() {
        return buoy;
    }

    public Version getVersion() {
        return version;
    }

    private Version getCurrentVersion() {
        String number = readString();
        int contentSize = readInt();
        byte[] content = new byte[contentSize];
        for (int i = 0; i < contentSize; i++)
            content[i] = readByte();

        return new Version(number, content);
    }

    private Buoy getCreateBuoy() {
        Buoy b = new Buoy();
        b.getId().set(readLong());
        b.getVersion().set(readString());
        b.getWho().set(readString());
        b.getUsage().set(Usage.values()[readInt()]);
        b.getSensors().setWith(readSensors());
        b.getDataCount().set(0);

        return b;
    }
    
    public Buoy getUpdateBuoy(){
        Buoy buoy = new Buoy();
        buoy.getId().set(readLong());
        buoy.getVersion().set(readString());
        buoy.getWho().set(readString());
        buoy.getUsage().set(Usage.values()[readInt()]);
        buoy.getSensors().setWith(readSensors());
        return buoy;
    }

    protected Sensors readSensors(){
        Sensors s = new Sensors();

        s.getSensor3DAcceleration().set(readBoolean());
        s.getSensor3DRotation().set(readBoolean());
        s.getSensorNorth().set(readBoolean());
        s.getSensorTop().set(readBoolean());
        s.getSensorBottom().set(readBoolean());
        s.getSensorTelemetry().set(readBoolean());

        return s;
    }
}
