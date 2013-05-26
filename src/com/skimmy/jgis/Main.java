package com.skimmy.jgis;

import com.skimmy.jgis.data.GeoPoint;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.jgis.metrics.GeoDistance;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeoPoint liberty = new GeoPoint(40.6893, -74.0445);
		GeoPointWithAccuracy bigben = new GeoPointWithAccuracy(51.50072, -0.12456, 5.0);
		System.out.println("Liberty Statue  " + liberty.toString());
		System.out.println("Big Ben         " + bigben.toString());
		System.out.println("distance        " + liberty.distanceFrom(bigben));
		System.out.println("Bearing         " + liberty.bearingTo(bigben));
		System.out.println(GeoDistance.getBearingDeg(50.5, 0, 51.5020, 0.0030));
	}

}
