package com.skimmy.jgis.data.nav;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@link NavigationSession} is a {@link LinkedBlockingQueue} extension that
 * performs computation over the {@link NavigationPoint} objects added to the
 * queue. This class is also {@link Runnable} so that a thread can be started to
 * perform a <em>navigation session</em>
 * 
 * @author Michele Schimd
 * 
 */
public class NavigationSession extends LinkedBlockingQueue<NavigationPoint>
		implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7314701452009344724L;

	public static final int STATE_STOPPED = 1;
	public static final int STATE_MOVING = 2;

	private static final int DEFAULT_WINDOW_SIZE = 3;
	private static final double DEFAULT_REST_SPEED_THRESHOLD = 2.35;

	// The window used to compute average speed
	private int windowSize;
	private NavigationPoint[] window;
	private int index;

	// variables to compute overall speed
	private double averageSpeed = 0;
	private NavigationPoint firstPoint = null;
	private NavigationPoint lastPoint = null;
	private double totalDistance;
	
	// lists of "relevant" points
	List<NavigationPoint> restPoints;

	public NavigationSession() {
		this(DEFAULT_WINDOW_SIZE);
	}

	public NavigationSession(int wSize) {
		// average speed window
		this.windowSize = wSize;
		this.window = new NavigationPoint[this.windowSize];
		this.index = 0;
		
		// relevant point lists
		this.restPoints = new LinkedList<NavigationPoint>();
	}

	@Override
	public void run() {
		while (true) {
			NavigationPoint point = null;
			try {
				point = this.take();
			} catch (InterruptedException e) {
				return;
			}
			if (point == null) {
				continue;
			}
			if (this.firstPoint == null) {
				this.firstPoint = point;
			}
			if (this.lastPoint != null) {
//				double d = (double) point.distanceFrom(lastPoint);
//				double t = (double) point.timeFrom(lastPoint) / 1000.0;
//				double v = d / t;
				this.totalDistance += point.distanceFrom(lastPoint);
			}
			this.lastPoint = point;
			this.window[this.index] = point;
			this.setAverageSpeed(this.avgSpeed());
			this.addRelevantPointToLists(point);
			System.out.println("" + this.totalDistance + "\t"
					+ this.getAverageSpeed() + "\t" + this.getOverallSpeed());
			this.index = (this.index + 1) % this.windowSize;
		}
	}
	
	public List<NavigationPoint> getRestPointsList() {
		return this.restPoints;
	}

	public synchronized double getAverageSpeed() {
		return averageSpeed;
	}

	private synchronized void setAverageSpeed(double averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public double getOverallSpeed() {
		double time = 0;
		if (this.firstPoint != null && this.lastPoint != null) {
			time = ((double) this.lastPoint.timeFrom(firstPoint)) / 1000.0;
		}
		return (time != 0 ? this.totalDistance / time : 0);
	}

	public int getMotionState() {
		double avgSpeed = this.getAverageSpeed();
		return (avgSpeed < DEFAULT_REST_SPEED_THRESHOLD ? STATE_STOPPED
				: STATE_MOVING);
	}
	
	private void addRelevantPointToLists(NavigationPoint actualPoint) {
		int motionState = this.getMotionState();
		if (motionState == STATE_STOPPED) {
			this.restPoints.add(actualPoint);
		}
	}

	private double avgSpeed() {
		if (this.window[0] == null) {
			return 0;
		}
		NavigationPoint prevPoint = window[index];
		double avg = 0;
		int count = 0;
		int idx = (this.index + 1) % this.windowSize;
		while (this.window[idx] == null) {
			idx = (idx + 1) % this.windowSize;
		}
		if (idx == this.index) {
			return 0;
		}
		prevPoint = window[idx];
		while (idx != this.index) {
			idx = (idx + 1) % this.windowSize;
			avg += this.window[idx].speedWithAccuracyFrom(prevPoint);
			count++;
			// avg += this.window[idx]
		}
		return (avg / (double) count);
	}
}
