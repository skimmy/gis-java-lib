package com.skimmy.jgis.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.skimmy.jgis.data.nav.NavigationPoint;
import com.skimmy.jgis.data.nav.NavigationSession;

public class LogFileParser {
	public static List<NavigationPoint> parseLogFile(String fileName)
			throws IOException {
		NavigationSession navSession = new NavigationSession(5);
		Thread thread = new Thread(navSession);
		thread.start();
		List<NavigationPoint> points = new LinkedList<NavigationPoint>();
		BufferedReader bReader = new BufferedReader(new FileReader(new File(
				fileName)));
		String line = null;
		while ((line = bReader.readLine()) != null) {
			// System.out.println(line);
			Scanner scanner = new Scanner(line);
			String date = scanner.next();
			String time = scanner.next();
			double lat = Double.valueOf(scanner.next()).doubleValue();
			double lon = Double.valueOf(scanner.next()).doubleValue();
			double acc = Double.valueOf(scanner.next()).doubleValue();
			SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
					Locale.US);
			Date d = null;
			try {
				d = simpleFormatter.parse(date + " " + time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NavigationPoint navPoint = new NavigationPoint(lat,lon,acc);
			navPoint.setTimestamp(d);
			points.add(navPoint);
			navSession.offer(navPoint);
			
		}
		bReader.close();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileOutputStream fos = new FileOutputStream("/Users/micheleschimd/Desktop/stops.kml");
		List<NavigationPoint> restPoints = navSession.getRestPointsList();
		for (NavigationPoint p : restPoints) {
			System.out.println(p.toString());
			fos.write((p.toKmlPlacemarkString() + "\n").getBytes());
		}
		fos.close();
		return points;
	}
}
