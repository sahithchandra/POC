package com.budget.manager;

import java.util.*;

public class Manager {

	private double balance = 0.0;
	private final List<Purchase> purchaseList = new ArrayList<>();
	private final List<Purchase> food = new ArrayList<>();
	private final List<Purchase> clothes = new ArrayList<>();
	private final List<Purchase> entertainment = new ArrayList<>();
	private final List<Purchase> other = new ArrayList<>();

	/**
	 * invoked by Add Income command in the main menu
	 * 
	 * @param income amount to be added to the balance
	 */
	public void addIncome(double income) {
		this.balance += income;
		System.out.printf(Locale.US, "Income was added!%n%n");
	}

	/**
	 * invoked by AddPurchaseSubMenu in the Menu class, creates and adds a purchase
	 * by its category
	 * 
	 * @param type        type of the purchase (see the list)
	 * @param productName name of the purchase
	 * @param price       price of the purchase
	 */
	public void addPurchase(PurchaseType type, String productName, double price) {
		Purchase purchase = new Purchase(type, productName, price);
		switch (purchase.getType()) {
		case FOOD:
			food.add(purchase);
			break;
		case CLOTHES:
			clothes.add(purchase);
			break;
		case ENTERTAINMENT:
			entertainment.add(purchase);
			break;
		case OTHER:
			other.add(purchase);
		default:
		}
		this.balance -= price;
		this.balance = Math.max(this.balance, 0.0);
		System.out.println("Purchase was added!");
	}

	/**
	 * invoked by showPurchaseLists method in Menu class to show a purchase list
	 * selected according to the type of purchases
	 * 
	 * @param type the type of purchases
	 */
	public void showPurchaseList(PurchaseType type) {
		List<Purchase> list;
		switch (type) {
		case FOOD:
			System.out.println("\nFood:");
			list = this.food;
			break;
		case CLOTHES:
			System.out.println("\nClothes:");
			list = this.clothes;
			break;
		case ENTERTAINMENT:
			System.out.println("\nEntertainment:");
			list = this.entertainment;
			break;
		case OTHER:
			System.out.println("\nOther:");
			list = this.other;
			break;
		case ALL:
			System.out.println("\nAll:");
			consolidatePurchaseLists();
		default:
			list = this.purchaseList;
		}
		if (list.isEmpty()) {
			System.out.println("Purchase list is empty");
		} else {
			list.forEach(System.out::println);
			double total = getTotalSumByCategory(list);
			System.out.println(String.format(Locale.US, "Total sum: $%.2f", total));
		}
	}

	/**
	 * consolidates all purchase lists into this.purchaseList
	 */
	private void consolidatePurchaseLists() {
		this.purchaseList.clear();
		this.purchaseList.addAll(this.food);
		this.purchaseList.addAll(this.clothes);
		this.purchaseList.addAll(this.entertainment);
		this.purchaseList.addAll(this.other);
	}

	/**
	 * invoked by Balance command in the main menu to show the balance
	 */
	public void showBalance() {
		System.out.printf(Locale.US, "%nBalance: $%.2f%n%n", this.balance);
	}

	public double getBalance() {
		return this.balance;
	}

	/**
	 * Invoked as a parameter of DBManager.save() method that saves all lists to a
	 * file
	 * 
	 * @return a list of purchase lists in the exactly same order as in the purchase
	 *         menu i.e. food, clothes, entertainment, other
	 */
	public List<List<Purchase>> getDatabase() {
		List<List<Purchase>> database = new ArrayList<>();
		database.add(this.food);
		database.add(this.clothes);
		database.add(this.entertainment);
		database.add(this.other);
		return database;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * invoked by DBManager.load() method to import lists loaded from the file
	 * 
	 * @param database the list of purchase lists
	 */
	public void importDB(List<List<Purchase>> database) {
		this.food.addAll(database.get(0));
		this.clothes.addAll(database.get(1));
		this.entertainment.addAll(database.get(2));
		this.other.addAll(database.get(3));
	}

	public List<Purchase> getPurchaseList() {
		consolidatePurchaseLists();
		return this.purchaseList;
	}

	public List<Purchase> getFoodList() {
		return this.food;
	}

	public List<Purchase> getClothesList() {
		return this.clothes;
	}

	public List<Purchase> getEntertainmentList() {
		return this.entertainment;
	}

	public List<Purchase> getOtherList() {
		return this.other;
	}

	/**
	 * invoked by Sort by Type command in the Analyze menu of the main menu creates
	 * a list of purchases where each purchase is the total of purchases in the
	 * respective category
	 * 
	 * @return the consolidated list of purchases
	 */
	public List<Purchase> getCategoryTotals() {
		List<Purchase> list = new ArrayList<>();
		list.add(new Purchase(PurchaseType.FOOD, "Food -", getTotalSumByCategory(this.food)));
		list.add(new Purchase(PurchaseType.CLOTHES, "Clothes -", getTotalSumByCategory(this.clothes)));
		list.add(
				new Purchase(PurchaseType.ENTERTAINMENT, "Entertainment -", getTotalSumByCategory(this.entertainment)));
		list.add(new Purchase(PurchaseType.OTHER, "Other -", getTotalSumByCategory(this.other)));
		return list;
	}

	/**
	 * calculates a total value of items in a list
	 * 
	 * @param list the list with items of Purchase class
	 * @return double number representing the total value
	 */
	private double getTotalSumByCategory(List<Purchase> list) {
		double total = 0.0;
		for (Purchase item : list) {
			total += item.getPrice();
		}
		return total;
	}

}
