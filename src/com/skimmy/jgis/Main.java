package com.skimmy.jgis;

import java.util.Date;
import java.util.List;

import com.skimmy.jgis.data.GeoPoint;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.jgis.data.nav.NavigationPoint;
import com.skimmy.jgis.data.nav.Route;

public class Main {

	private static void testGeoPoints() {
		System.out.println("\n******** GeoPoints test ********");
		GeoPoint liberty = new GeoPoint(40.6893, -74.0445);
		GeoPointWithAccuracy bigben = new GeoPointWithAccuracy(51.50072,
				-0.12456, 5.0);
		System.out.println("Liberty Statue  " + liberty.toString());
		System.out.println("Big Ben         " + bigben.toString());
		System.out.println("distance        " + liberty.distanceFrom(bigben));
		System.out.println("Bearing         " + liberty.bearingTo(bigben));		
	}

	private static void testRoute() {
		System.out.println("\n******** Route test ********");
		Route route = new Route();
		long now = System.currentTimeMillis();
		NavigationPoint tmp = new NavigationPoint(51.50072, -0.12456);
		tmp.setTimestamp(new Date(now));
		route.append(tmp);
		
		tmp = new NavigationPoint(51.50081, -0.12003);
		tmp.setTimestamp(new Date(now + 6*60*1000));
		route.append(tmp);
		
		tmp = new NavigationPoint(51.50334, -0.1196);
		tmp.setTimestamp(new Date(now + 10*60*1000));
		route.append(tmp);
		
		List<Double> distances = route.getDistancesList();
		String dists = "";
		for (Double d : distances) {
			
			dists += Math.round(d.doubleValue()) + " ";
		}
		
		System.out.println("Start:    " + route.getFirstPoint()); 
		System.out.println("End:      " + route.getLastPoint());
		System.out.println("Distance: " + route.getTotalDistance());
		System.out.println("Steps:    " + dists);
		System.out.println("Time:     " + route.getRouteTimeSeconds());
		System.out.println("Avg speed " + route.getAverageSpeed()*3.6 + " Km/h");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testGeoPoints();
		testRoute();
	}

}
