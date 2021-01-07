package fr.ensisa.gmv.buoys.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private Buoys buoys;
	private BuoyData last;
	private ObservableList<BuoyData> data;

	public Buoys getBuoys() {
		if (buoys == null) buoys = new Buoys();
		return buoys;
	}

	public BuoyData getLast() {
		if (last == null) last = new BuoyData();
		return last;
	}

	public ObservableList<BuoyData> getData() {
		if (data == null) {
			data = FXCollections.observableArrayList();
		}
		return data;
	}

}
