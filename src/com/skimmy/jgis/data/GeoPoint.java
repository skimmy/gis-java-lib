package com.skimmy.jgis.data;

import com.skimmy.jgis.metrics.GeoDistance;

public class GeoPoint {

	private double lat;
	private double lon;

	public GeoPoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	/**
	 * Computes the Haversine distance between this point and the point passed
	 * as parameters
	 * 
	 * @param other
	 * @return
	 */
	public double distanceFrom(GeoPoint other) {
		return GeoDistance.haversineDistance(this.lat, this.lon, other.lat,
				other.lon);
	}
	
	@Override
	public String toString() {
		return "(" + this.lat + ", " + this.lon + ")";
	}
}
