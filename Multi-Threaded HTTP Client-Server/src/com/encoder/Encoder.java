package com.encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.util.UtilityHelper;

/*Encoder is a class which reads an input sequence of symbols, 
* groups the symbols into strings, and represents the strings with integer codes 
* @Author: Sahith chandra Ananthoju 
* 
*/

public class Encoder {

	static Map<String, Integer> asciiMap = new HashMap<>();

	// static block to initialize the asciiMap with all the 256 ASCII characters.
	static {
		for (int i = 0; i < 255; i++) {
			asciiMap.put("" + (char) i, i);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		/*
		 * Assigning the ASCII text file whose name is specified as a command line
		 * argument
		 */
		File file = new File(args[0]);
		// Assigning the bit length which is specified also as command line argument
		String bitLength = args[1];
		String input = "";
		String encodedInput = "";

		// using BufferedReader object to read the file
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			// reading input from the input ASCII text file
			input = bufferedReader.readLine();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		/*
		 * passing received input string from ASCII text file and bit length from
		 * command line arguments
		 */
		encodedInput = encoding(input, bitLength);
		// printing encodedInput
		System.out.println("encodedInput:" + encodedInput);
		String encodedBinaryFormat = UtilityHelper.bitFormat(encodedInput);
		// printing encodedBinaryFormat
		System.out.println("encodedBinaryFormat:" + encodedBinaryFormat);

		/*
		 * creating a new file with the same file name as received in command line
		 * arguments.
		 */
		String filePath = args[0].replace(".txt", ".lzw");
		try {
			Files.write(Paths.get(filePath), encodedBinaryFormat.getBytes());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * this method is used to Encode the given input.
	 *
	 * @param input     the input
	 * @param bitLength the bit length
	 * @return the string
	 */
	public static String encoding(String input, String bitLength) {
		String output = "";
		// setting the maxTableSize as given in the problem statement
		double maxTableSize = Math.pow(2, Integer.parseInt(bitLength));
		// splitting the input string
		String[] inputArray = input.split("");

		String string = "";
		String symbol = "";

		for (int i = 0; i < inputArray.length; i++) {
			symbol = inputArray[i];
			// fetching the asciiValue from asciiMap
			Integer stringAsciiLocation = asciiMap.get(string + symbol);
			if (stringAsciiLocation != null) {
				string = string + symbol;
			} else {
				output += asciiMap.get(string) + " ";
				// validating the size of AsciiMap
				if (asciiMap.size() < maxTableSize)
					asciiMap.put(string + symbol, asciiMap.size() + 1);
				string = symbol;
			}
		}
		output += asciiMap.get(string) + " ";
		return output;
	}

}
