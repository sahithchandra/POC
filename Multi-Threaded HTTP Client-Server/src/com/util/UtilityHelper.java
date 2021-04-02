package com.util;

public class UtilityHelper {
	
	/**
	 * this method is used to convert into 16 Bit binary format.
	 *
	 * @param result the result
	 * @return the string
	 */
	public static String bitFormat(String result) {
		// splitting the string
		String[] encodedOutput = result.split(" ");
		String output = "";
		for (int i = 0; i < encodedOutput.length; i++) {
			// converting into 16-bit binary format
			output += decToBinary(Integer.parseInt(encodedOutput[i]));
		}
		return output;

	}

	/**
	 * This method is used to convert Decimal to 16-bit binary number.
	 *
	 * @param result the result
	 * @return the string
	 */
	public static String decToBinary(int result) {

		StringBuilder binarynumber = new StringBuilder("");
		for (int j = 15; j >= 0; j--) {
			int k = result >> j;

			if (j == 7)
				binarynumber.append(" ");

			if ((k & 1) != 0)
				binarynumber.append("1");
			else
				binarynumber.append("0");
		}
		binarynumber.append(" ");
		return binarynumber.toString();
	}

	
	/**
	 * this method is used to convert Binary number to decimal.
	 *
	 * @param binaryString the binary string
	 * @return the int
	 */
	public static int binaryToDecimal(String binaryString) {
		return Integer.parseInt(binaryString, 2);
	}
}
