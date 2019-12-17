package com.metro.dao;

import java.util.List;

import com.metro.beans.Metro;

public interface MetroDAO {

	public Metro getStationDistance(String stationName);

	public List<Metro> getCrossingStationDistance(String color);

}
