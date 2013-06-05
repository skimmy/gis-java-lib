package com.skimmy.jgis.data.nav;

import java.util.Date;

import com.skimmy.jgis.data.GeoPointWithAccuracy;

public class NavigationPoint extends GeoPointWithAccuracy {

	private Date timestamp;
	
	public NavigationPoint(double lat, double lon) {
		super(lat, lon, 0.0);
	}

	public NavigationPoint(double lat, double lon, double acc) {
		super(lat, lon, acc);
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the milliseconds elapsed since the <code>other</code> point. If
	 * either of two points have not a timestamp set, the method returns 0
	 * 
	 * @param other
	 *            a {@link NavigationPoint} since which the elapsed time is
	 *            computed
	 * @return Milliseconds elapsed since
	 *         <code>other</other> point or zero if either of
	 * the two points have no timestamp
	 */
	public long timeFrom(NavigationPoint other) {
		if (timestamp != null && other.timestamp != null) {
			return (this.timestamp.getTime() - other.timestamp.getTime());
		}
		return 0;
	}
	
	public String toKmlPlacemarkString() {
		String output = "<Placemark>";
		output += "<Point><coordinates>";
		output += "" + this.getLon() + "," + this.getLat();
		output += "</coordinates></Point>";
		output += "</Placemark>";
		return output;
	}

}
