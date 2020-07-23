package com.budget.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class DBManager {
	private final String fileName = "purchases.txt";
	private final File file = new File(fileName);
	private final String[] SECTIONS = { "[--food--]", "[--end of food--]", "[--clothes--]", "[--end of clothes--]",
			"[--entertainment--]", "[--end of entertainment--]", "[--other--]", "[--end of other--]" };

	/**
	 * Invoked by Save command in the main menu
	 * 
	 * @param database a list of purchase lists in the exactly same order as in the
	 *                 Purchase submenu i.e. Food, Clothes, Entertainment, Other
	 * @param balance  the balance on the account
	 */
	public void save(List<List<Purchase>> database, double balance) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(balance + "\n");
			int sectionDelimiter = 0;
			for (List<Purchase> list : database) {
				fw.write(SECTIONS[sectionDelimiter++] + "\n");
				for (Purchase purchase : list) {
					fw.write(purchase.toString() + "\n");
				}
				fw.write(SECTIONS[sectionDelimiter++] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nPurchases were saved!\n");
	}

	/**
	 * Invoked by Load command in the main menu, creates lists of purchases from the
	 * file
	 * 
	 * @param manager account manager which will import the purchase lists generated
	 *                by the method the order of the lists is the same as everywhere
	 *                in the app: food, clothes, entertainment, other
	 */
	public void load(Manager manager) {
		double balance;
		List<List<Purchase>> database = new ArrayList<>();
		try {
			Scanner fileScanner = new Scanner(file);
			balance = Double.parseDouble(fileScanner.nextLine());
			manager.setBalance(balance);
			NumberFormat nf = NumberFormat.getInstance(Locale.US);

			int index = 0;
			while (index < SECTIONS.length) {
				if (SECTIONS[index++].equals(fileScanner.nextLine())) {
					List<Purchase> list = new ArrayList<>();
					while (fileScanner.hasNextLine()) {
						String input = fileScanner.nextLine();
						if (SECTIONS[index].equals(input)) {
							break;
						} else {
							PurchaseType purchaseType;
							switch (index) {
							case 0:
								purchaseType = PurchaseType.FOOD;
								break;
							case 2:
								purchaseType = PurchaseType.CLOTHES;
								break;
							case 3:
								purchaseType = PurchaseType.ENTERTAINMENT;
								break;
							case 4:
							default:
								purchaseType = PurchaseType.OTHER;
							}
							String name = input.substring(0, input.lastIndexOf("$") - 1);
							double price = nf.parse(input.substring(input.lastIndexOf("$") + 1)).doubleValue();
							Purchase purchase = new Purchase(purchaseType, name, price);
							list.add(purchase);
						}

					}
					database.add(list);
					index++;
				} else {
					System.out.println("Importing gone wrong");
					return;
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		manager.importDB(database);
		System.out.println("\nPurchases were loaded!\n");
	}
}
