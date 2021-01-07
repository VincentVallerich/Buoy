package fr.ensisa.gmv.buoys.config.network;

import java.io.IOException;
import java.io.OutputStream;

import fr.ensisa.gmv.buoys.config.model.Buoy;
import fr.ensisa.gmv.buoys.config.model.Sensors;
import fr.ensisa.gmv.buoys.config.model.Version;
import fr.ensisa.gmv.buoys.network.Protocol;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class ConfigWriter extends BasicAbstractWriter {

    public ConfigWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createReceiveCurrentVersion() {
		writeInt(Protocol.GET_CONFIG_GET_VERSION);
	}

	public void createNewVersion(Version version) {
	}

	public void createDeleteBuoy(long id) {
    	writeInt(Protocol.GET_CONFIG_DELETE_BUOY);

	}

	public void createCreateBuoy(Buoy buoy) {
    	writeInt(Protocol.GET_CONFIG_CREATE_BUOY);
    	writeBuoy(buoy);
	}

	public void createUpdateBuoy(Buoy buoy) {
    	writeInt(Protocol.GET_CONFIG_UPDATE_BUOY);
		writeBuoy(buoy);
	}

	public void createGetBuoy(long id) {
		writeInt(Protocol.GET_CONFIG_GET_BUOY);
		writeLong(id);
	}

	public void createGetBuoyList(String who) {
		writeInt(Protocol.GET_CONFIG_GET_BUOYLIST);
		writeNullableString(who);
	}

	public void createClearDataBuoy(long id) {
	}

	private void writeSensor(Sensors s){
		writeBoolean(s.getSensor3DAcceleration().get());
		writeBoolean(s.getSensor3DRotation().get());
		writeBoolean(s.getSensorNorth().get());
		writeBoolean(s.getSensorBottom().get());
		writeBoolean(s.getSensorTelemetry().get());
	}

	private void writeBuoy(Buoy buoy){
    	if (buoy == null) throw new Error("Buoy connot be null");
		writeLong(buoy.getId().get());
		writeNullableString(buoy.getVersion().get());
		writeNullableString(buoy.getWho().get());
		writeInt(buoy.getUsage().get().ordinal());
		writeSensor(buoy.getSensors());
		writeInt(buoy.getDataCount().get());
	}

	private void deleteBuoy(Buoy buoy){
    	if (buoy == null) throw new Error("Buoy cannot be null");
    	writeLong(buoy.getId().get());
	}

	private void writeNullableString(String s){
    	if (s == null){
    		writeString("null");
		} else {
    		writeString(s);
		}
	}
}
