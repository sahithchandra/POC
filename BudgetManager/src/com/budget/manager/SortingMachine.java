package com.budget.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SortingMachine {
	List<Purchase> list = new ArrayList<>();

	/**
	 * Invoked by "sort by a category" commands in the Analyze submenus to sort
	 * costs in the descending order
	 * 
	 * @param purchaseList a list of purchase objects
	 * @param category     a name of the category, used as title: all, types, food,
	 *                     etc.
	 */
	public void sortByCategory(List<Purchase> purchaseList, String category) {
		list.addAll(purchaseList);
		if (list.isEmpty()) {
			System.out.println("\nPurchase list is empty!");
			return;
		} else {
			for (int i = list.size() - 1; i > 0; i--) {
				for (int j = 0; j < i; j++) {
					if (list.get(j).getPrice() < list.get(j + 1).getPrice()) {
						Collections.swap(list, j, j + 1);
					}
				}
			}
		}
		System.out.printf("%n%s:%n", category);
		list.forEach(System.out::println);
		double total = 0.0;
		for (Purchase item : list) {
			total += item.getPrice();
		}
		System.out.println(String.format(Locale.US, "Total sum: $%.2f", total));
	}
}
