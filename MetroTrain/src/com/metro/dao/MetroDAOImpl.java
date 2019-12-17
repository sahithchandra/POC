package com.metro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.metro.beans.Metro;

public class MetroDAOImpl implements MetroDAO {

	String query = "select COLOR,DISTANCE,DURATION,FARE,STATION from METRO_TABLE where STATION =:stationName";

	String crossingStationsQuery = "select DISTANCE,DURATION,FARE,STATION from METRO_TABLE where COLOR=: color";
	private DataSource dataSource;

	public MetroDAOImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Metro getStationDistance(String stationName) {

		Metro metro = null;

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, stationName);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				metro = new Metro();
				metro.setColor(resultSet.getString("COLOR"));
				metro.setDistance(resultSet.getFloat("DISTANCE"));
				metro.setDuration(resultSet.getInt("DURATION"));
				metro.setFare(resultSet.getInt("FARE"));
				metro.setStation(resultSet.getString("STATION"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return metro;
	}

	@Override
	public List<Metro> getCrossingStationDistance(String color) {
		Metro metro = null;
		List<Metro> metroList = new ArrayList<Metro>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(crossingStationsQuery);
			ps.setString(1, color);
			rs = ps.executeQuery();
			while (rs.next()) {
				metro = new Metro();

				metro.setDistance(rs.getFloat("DISTANCE"));
				metro.setDuration(rs.getInt("DURATION"));
				metro.setFare(rs.getInt("FARE"));
				metro.setStation(rs.getString("STATION"));

				metroList.add(metro);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return metroList;
	}

}
