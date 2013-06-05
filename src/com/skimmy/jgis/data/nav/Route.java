package com.skimmy.jgis.data.nav;

import java.util.LinkedList;
import java.util.List;

public class Route {
	private LinkedList<NavigationPoint> points;

	public Route() {
		this.points = new LinkedList<NavigationPoint>();
	}

	public int size() {
		return this.size();
	}

	public void append(NavigationPoint point) {
		this.points.addLast(point);
	}

	public NavigationPoint getFirstPoint() {
		return this.points.getFirst();
	}

	public NavigationPoint getLastPoint() {
		return this.points.getLast();
	}

	public List<NavigationPoint> toList() {
		return this.points;
	}

	public double getTotalDistance() {
		int N = this.points.size();
		if (N <= 1) {
			return 0.0;
		}
		NavigationPoint last = this.points.getFirst();
		double distance = 0.0;
		for (NavigationPoint p : this.points) {
			distance += p.distanceFrom(last);
			last = p;
		}
		return distance;
	}

	public List<Double> getDistancesList() {
		LinkedList<Double> distances = new LinkedList<Double>();
		NavigationPoint last = null;
		for (NavigationPoint p : this.points) {
			if (last != null) {
				distances.addLast(new Double(p.distanceFrom(last)));
			}
			last = p;
		}
		return distances;
	}

	public long getRouteTimeSeconds() {
		return (this.points.getLast().timeFrom(this.points.getFirst()) / 1000);
	}
	
	public double getAverageSpeed() {
		long time = this.getRouteTimeSeconds();
		if (time == 0) {
			return 0.0;
		}
		double meters = this.getTotalDistance();
		return (meters / (double)time);
	}
}
