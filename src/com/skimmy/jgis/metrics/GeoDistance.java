package com.skimmy.jgis.metrics;

import com.skimmy.jgis.data.Constants;

public class GeoDistance {
	public static double haversineDistance(double lat1, double lon1,
			double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return Constants.EARTH_MEAN_RADIUS_METER * c;
	}

	// TODO: Rewrite: WRONG!!!
	public static double getBearingDeg(double lat1, double lon1, double lat2, double lon2) {
		double dLon = Math.toRadians(lon2 - lon1);
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) -
		        Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
		return (Math.toDegrees(Math.atan(y / x)));
	}
	
}
