package com.metro.controller;

import com.metro.service.MetroService;

public class MetroController {

	private MetroService metroService;

	public MetroController(MetroService metroService) {
		super();
		this.metroService = metroService;
	}

	public int getFare(String sourceStation, String destinationStation) {
		int fare;
		fare = metroService.getFare(sourceStation, destinationStation);
		return fare;
	}

}
