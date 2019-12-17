package com.metro.service;

import java.util.List;

import com.metro.beans.Metro;
import com.metro.dao.MetroDAO;

public class MetroServiceImpl implements MetroService {

	private MetroDAO metroDAO;

	public MetroServiceImpl(MetroDAO metroDAO) {
		super();
		this.metroDAO = metroDAO;
	}

	@Override
	public int getFare(String sourceStation, String destinationStation) {

		/*
		 * float sourceStationDistance = 0; float destinationStationDistance = 0; float
		 * actualDistance = 0; String sourceStationColor = null; String
		 * destinationStationColor = null; String crossingStation = "ameerpet"; float
		 * sourceCrossingDistance = 0; float destinationCrossingDistance = 0; int fare =
		 * 0; List<Metro> metroList = metroDAO.getDistance(sourceStation.toUpperCase(),
		 * destinationStation.toUpperCase()); if (metroList.size() > 0) {
		 * sourceStationDistance = metroList.get(0).getDistance();
		 * destinationStationDistance = metroList.get(1).getDistance();
		 * System.out.println("sourceStationDistance:" + sourceStationDistance);
		 * System.out.println("destinationStationDistance:" +
		 * destinationStationDistance); sourceStationColor =
		 * metroList.get(0).getColor(); destinationStationColor =
		 * metroList.get(1).getColor(); System.out.println("sourceStationColor:" +
		 * sourceStationColor); System.out.println("destinationStationColor:" +
		 * destinationStationColor);
		 * 
		 * if (sourceStation.equals(crossingStation) ||
		 * destinationStation.equals(crossingStation)) { actualDistance =
		 * Math.abs(destinationStationDistance - sourceStationDistance);
		 * System.out.println("actualDistance:" + actualDistance);
		 * 
		 * fare = calculateFareByDistance(actualDistance); }
		 * 
		 * else if (sourceStationColor.equals(destinationStationColor)) { actualDistance
		 * = Math.abs(destinationStationDistance - sourceStationDistance);
		 * System.out.println("actualDistance:" + actualDistance); fare =
		 * calculateFareByDistance(actualDistance);
		 * 
		 * } else { List<Metro> sourceCrossingList =
		 * metroDAO.getDistance(sourceStation.toUpperCase(),
		 * crossingStation.toUpperCase()); List<Metro> destinationCrossingList =
		 * metroDAO.getDistance(crossingStation.toUpperCase(),
		 * destinationStation.toUpperCase()); sourceCrossingDistance = Math
		 * .abs(sourceCrossingList.get(0).getDistance() -
		 * sourceCrossingList.get(1).getDistance()); destinationCrossingDistance =
		 * Math.abs( destinationCrossingList.get(0).getDistance() -
		 * destinationCrossingList.get(1).getDistance()); actualDistance =
		 * sourceCrossingDistance + destinationCrossingDistance;
		 * System.out.println("actualDistance:" + actualDistance); fare =
		 * calculateFareByDistance(actualDistance); } }
		 */

		int fare = 0;
		float sourceStationDistance = 0;
		float destinationStationDistance = 0;
		Metro sourceStationDetails = null;
		float totalDistance = 0;
		Metro destinationStationDetails = null;
		List<Metro> crossingStationsList = null;
		// get source and destination station details list

		sourceStationDetails = metroDAO.getStationDistance(sourceStation.toUpperCase());
		destinationStationDetails = metroDAO.getStationDistance(destinationStation.toUpperCase());
		sourceStationDistance = sourceStationDetails.getDistance();
		destinationStationDistance = destinationStationDetails.getDistance();

		// get crossing stations distance

		if (sourceStationDetails.getColor().equals(destinationStationDetails.getColor())) {

			totalDistance = Math.abs(sourceStationDistance - destinationStationDistance);
			System.out.println("Distance between " + sourceStationDetails.getStation() + " & "
					+ destinationStationDetails.getStation() + "is:" + totalDistance);

		} else {
			crossingStationsList = metroDAO.getCrossingStationDistance("BLACK");
			for (Metro metro : crossingStationsList) {

				totalDistance = Math.abs(sourceStationDistance - metro.getDistance())
						+ Math.abs(destinationStationDistance - metro.getDistance());
				System.out.println("Distance between " + sourceStationDetails.getStation() + " & "
						+ destinationStationDetails.getStation() + "is:" + totalDistance);

			}
		}
		fare = calculateFareByDistance(totalDistance);
		System.out.println("Fare for " + sourceStationDetails.getStation() + " & "
				+ destinationStationDetails.getStation() + "is:" + fare);
		return fare;
	}

	private int calculateFareByDistance(float actualDistance) {
		int fare = 0;
		if (actualDistance > 0 && actualDistance <= 2)
			fare = 10;
		else if (actualDistance > 2 && actualDistance <= 4)
			fare = 15;
		else if (actualDistance > 4 && actualDistance <= 6)
			fare = 25;
		else if (actualDistance > 6 && actualDistance <= 8)
			fare = 30;
		else if (actualDistance > 8 && actualDistance <= 10)
			fare = 35;
		else if (actualDistance > 10 && actualDistance <= 15)
			fare = 40;
		else if (actualDistance > 15 && actualDistance <= 18)
			fare = 45;
		else if (actualDistance > 18 && actualDistance <= 22)
			fare = 50;
		else if (actualDistance > 22 && actualDistance <= 25)
			fare = 55;
		else if (actualDistance > 26)
			fare = 60;
		return fare;
	}

}
