package com.metro.beans;

public class Metro {

	private String station;
	private String color;
	private float distance;
	private int duration;
	private int fare;
	private String crossingIndicator;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public String getCrossingIndicator() {
		return crossingIndicator;
	}

	public void setCrossingIndicator(String crossingIndicator) {
		this.crossingIndicator = crossingIndicator;
	}

}
