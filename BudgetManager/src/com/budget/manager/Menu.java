package com.budget.manager;

import java.util.Scanner;

public class Menu {
	Manager manager = new Manager();
	DBManager dbManager = new DBManager();
	private boolean isOn = true;
	private final Scanner scanner = new Scanner(System.in);

	public void start() {
		while (isOn) {
			isOn = show();
		}
	}

	/**
	 * shows main menu
	 * 
	 * @return exits the main menu to the main() method
	 */
	private boolean show() {
		System.out.println(
				"Choose your action:\n" + "1) Add income\n" + "2) Add purchase\n" + "3) Show list of purchases\n"
						+ "4) Balance\n" + "5) Save\n" + "6) Load\n" + "7) Analyze (Sort)\n" + "0) Exit");

		String command = this.scanner.nextLine().trim();
		switch (command) {
		case "1":
			System.out.println("\nEnter income:");
			manager.addIncome(Double.parseDouble(this.scanner.nextLine()));
			break;
		case "2":
			addPurchaseSubMenu();
			break;
		case "3":
			showPurchaseLists();
			break;
		case "4":
			manager.showBalance();
			break;
		case "5":
			dbManager.save(manager.getDatabase(), manager.getBalance());
			break;
		case "6":
			dbManager.load(manager);
			break;
		case "7":
			sortSubMenu();
			break;
		case "0":
			System.out.println("\nBye!");
			return false;
		default:
		}
		return true;
	}

	/**
	 * invoked by Analyze(Sort) command in the main menu to display sorting options
	 */
	private void sortSubMenu() {
		while (true) {
			System.out.println("\nHow do you want to sort?\n" + "1) Sort all purchases\n" + "2) Sort by type\n"
					+ "3) Sort certain type\n" + "4) Back");
			String command = this.scanner.nextLine().trim();
			switch (command) {
			case "2":
				new SortingMachine().sortByCategory(manager.getCategoryTotals(), "Types");
				break;
			case "3":
				sortCategorySelectionSubMenu();
				break;
			case "4":
				System.out.println();
				return;
			case "1":
			default:
				new SortingMachine().sortByCategory(manager.getPurchaseList(), "All");
			}

		}
	}

	/**
	 * invoked by sortSubMenu to display purchase type categories available for
	 * sorting
	 */
	private void sortCategorySelectionSubMenu() {
		System.out.println(
				"\nChoose the type of purchase\n" + "1) Food\n" + "2) Clothes\n" + "3) Entertainment\n" + "4) Other");
		String command = this.scanner.nextLine().trim();
		switch (command) {
		case "1":
			new SortingMachine().sortByCategory(manager.getFoodList(), "Food");
			break;
		case "2":
			new SortingMachine().sortByCategory(manager.getClothesList(), "Clothes");
			break;
		case "3":
			new SortingMachine().sortByCategory(manager.getEntertainmentList(), "Entertainment");
			break;
		case "4":
			new SortingMachine().sortByCategory(manager.getOtherList(), "Other");
			break;
		default:
		}
	}

	/**
	 * invoked from show() method to display Show Purchase List submenu
	 */
	private void showPurchaseLists() {
		while (true) {
			System.out.println("\nChoose the type of purchase\n" + "1) Food\n" + "2) Clothes\n" + "3) Entertainment\n"
					+ "4) Other\n" + "5) All\n" + "6) Back");
			String command = this.scanner.nextLine().trim();
			PurchaseType type;
			switch (command) {
			case "1":
				type = PurchaseType.FOOD;
				break;
			case "2":
				type = PurchaseType.CLOTHES;
				break;
			case "3":
				type = PurchaseType.ENTERTAINMENT;
				break;
			case "4":
				type = PurchaseType.OTHER;
				break;
			case "6":
				System.out.println();
				return;
			case "5":
			default:
				type = PurchaseType.ALL;
			}
			manager.showPurchaseList(type);
		}
	}

	/**
	 * invoked by Add Purchase command in the main menu to show purchase categories
	 * submenu
	 */
	private void addPurchaseSubMenu() {
		while (true) {
			System.out.println("\nChoose the type of purchase\n" + "1) Food\n" + "2) Clothes\n" + "3) Entertainment\n"
					+ "4) Other\n" + "5) Back");
			String command = this.scanner.nextLine();
			PurchaseType type;
			switch (command) {
			case "1":
				type = PurchaseType.FOOD;
				break;
			case "2":
				type = PurchaseType.CLOTHES;
				break;
			case "3":
				type = PurchaseType.ENTERTAINMENT;
				break;
			case "5":
				System.out.println();
				return;
			case "4":
			default:
				type = PurchaseType.OTHER;
			}

			System.out.println("\nEnter purchase name:");
			String productName = this.scanner.nextLine();
			System.out.println("Enter its price:");
			double price = Double.parseDouble(this.scanner.nextLine());
			manager.addPurchase(type, productName, price);
		}
	}

}
