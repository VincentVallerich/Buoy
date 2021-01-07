package fr.ensisa.gmv.buoys.service.network;

import java.io.InputStream;

import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.buoys.server.model.Measures;
import fr.ensisa.gmv.buoys.server.model.State;
import fr.ensisa.gmv.buoys.service.model.Battery;
import fr.ensisa.gmv.buoys.service.model.BuoyData;
import fr.ensisa.gmv.buoys.service.model.Location;
import fr.ensisa.gmv.network.BasicAbstractReader;

public class ServiceReader extends BasicAbstractReader {

    private BuoyData buoyData;
    public ServiceReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
        this.buoyData = null;
    }

    public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_KO: break;
        case Protocol.GET_SESSION_BUOY_LAST_TICK: getBuoyLastTick(); break;
        }
    }

    private void getBuoyLastTick() {
        this.buoyData = getBuoyDataLastTick();
    }

    public BuoyData getBuoyData() {
        return buoyData;
    }

    private BuoyData getBuoyDataLastTick() {
        long id = readLong();
        long date = readLong();
        int type = readInt();
        Location location = readLocation();
        State state = readState();
        Battery battery = readBattery();
        Measures measures = readMesures();

//        if (type == 0) this.buoyData = new BuoyData(date, id, location, state, battery);
//        if (type == 1) this.buoyData = new BuoyData(date, id, location, measures);
        return null;
    }

    public Location readLocation() {
        float longitude = readFloat();
        float latitude = readFloat();
        float altitude = readFloat();

        return new Location(longitude, latitude, altitude);
    }

    public State readState() {
        int state = readInt();
        int detail = readInt();

        return new State(state, detail);
    }

    public Battery readBattery() {
        int level = readInt();
        int temperature = readInt();
        int load = readInt();
        int plug = readInt();
        int discharge = readInt();
        int cycleCount = readInt();

        return new Battery(level, temperature, load, Battery.Plug.values()[plug], discharge, cycleCount);
    }

    public Measures readMesures() {
        Measures measures = new Measures();
        measures.setAcceleration_X(readFloat());
        measures.setAcceleration_Y(readFloat());
        measures.setAcceleration_Z(readFloat());
        measures.setRotation_X(readFloat());
        measures.setRotation_Y(readFloat());
        measures.setRotation_Z(readFloat());
        measures.setNorth(readFloat());
        measures.setTop_temperature(readFloat());
        measures.setTop_humidity(readFloat());
        measures.setTop_light(readFloat());
        measures.setTop_ir(readFloat());
        measures.setBottom_temperature(readFloat());
        measures.setBottom_humidity(readFloat());
        measures.setBottom_light(readFloat());
        measures.setBottom_ir(readFloat());
        measures.setTelemetry_front(readFloat());
        measures.setTelemetry_back(readFloat());
        measures.setTelemetry_left(readFloat());
        measures.setTelemetry_right(readFloat());

        return measures;
    }
}
