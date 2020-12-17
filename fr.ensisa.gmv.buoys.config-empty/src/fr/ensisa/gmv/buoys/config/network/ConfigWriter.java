package fr.ensisa.gmv.buoys.config.network;

import java.io.OutputStream;

import fr.ensisa.gmv.buoys.config.model.Buoy;
import fr.ensisa.gmv.buoys.config.model.Version;
import fr.ensisa.gmv.network.BasicAbstractWriter;

public class ConfigWriter extends BasicAbstractWriter {

    public ConfigWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void createReceiveCurrentVersion() {
	}

	public void createNewVersion(Version version) {
	}

	public void createGetBuoy(long id) {
	}

	public void createGetBuoyList(String who) {
	}

	public void createDeleteBuoy(long id) {
	}

	public void createCreateBuoy(Buoy buoy) {
	}

	public void createUpdateBuoy(Buoy buoy) {
	}

	public void createClearDataBuoy(long id) {
	}

}
