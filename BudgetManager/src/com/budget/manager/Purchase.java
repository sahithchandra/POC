package com.budget.manager;

import java.util.Locale;

public class Purchase {

	private final PurchaseType type;
	private final double price;
	private final String name;

	Purchase(PurchaseType type, String name, double price) {
		this.type = type;
		this.name = name;
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public PurchaseType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%s $%.2f", this.name, this.price);
	}

}
