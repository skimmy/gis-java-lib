package com.skimmy.jgis.data;

public class GeoPointWithAccuracy extends GeoPoint {
	private double accuracy;
	
	public GeoPointWithAccuracy(double lat, double lon, double acc) {
		super(lat,lon);
		this.accuracy = acc;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	@Override
	public String toString() {
		return super.toString() + " +/- " + this.accuracy;
	}
}
