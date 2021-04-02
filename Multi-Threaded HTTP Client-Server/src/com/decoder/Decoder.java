package com.decoder;

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

/*Encoder is a class which converts integer codes into the strings they represent
 * 
 * @Author: Sahith chandra Ananthoju  
 */

public class Decoder {

	// static block to initialize the asciiMap with all the 256 ASCII characters.
	static Map<Integer, String> asciiMap = new HashMap<>();
	static {
		for (int i = 0; i < 255; i++) {
			asciiMap.put(i, "" + (char) i);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		/*
		 * Assigning the encoded file whose name is specified as a command line argument
		 */
		File file = new File(args[0]);
		// Assigning the bit length which is specified also as command line argument
		String bitLength = args[1];
		String binaryInput = "";
		String decimalInput = "";

		// using BufferedReader object to read the file
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			// reading input from the input encoded file
			binaryInput = bufferedReader.readLine();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// converting the 16-bit binary numbers into decimal numbers
		String[] inputBinaryNumbers = binaryInput.split(" ");
		for (int i = 0; i < inputBinaryNumbers.length; i += 2) {
			decimalInput += UtilityHelper.binaryToDecimal(inputBinaryNumbers[i] + inputBinaryNumbers[i + 1]) + " ";
		}

		/*
		 * passing received input string from encoded file and bit length from command
		 * line arguments
		 */
		String decodedOutput = decoding(decimalInput, bitLength);
		// printing decodedOutput
		System.out.println("decodedOutput:" + decodedOutput);

		/*
		 * creating a new file with the same file name as received in command line
		 * arguments.
		 */
		String filePath = args[0].replace(".lzw", "_decoded.txt");

		try {
			Files.write(Paths.get(filePath), decodedOutput.getBytes());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * this method is used to decode the given input.
	 *
	 * @param input     the input
	 * @param bitLength the bit length
	 * @return the string
	 */
	public static String decoding(String input, String bitLength) {

		String decodedOutput = "";
		// setting the maxTableSize as given in the problem statement
		double maxTableSize = Math.pow(2, Integer.parseInt(bitLength));

		String[] inputArray = input.split(" ");

		String string = "";
		String newString = "";
		String code = "";

		code = inputArray[0];
		// fetching the ASCII character from asciiMap
		string = asciiMap.get(Integer.parseInt(code));
		decodedOutput += string;

		for (int i = 1; i < inputArray.length; i++) {

			code = inputArray[i];

			if (asciiMap.get(Integer.parseInt(code)) == null) {
				newString = string + string.charAt(0);
			} else {
				newString = asciiMap.get(Integer.parseInt(code));
			}
			decodedOutput += newString;
			// validating the size of AsciiMap
			if (asciiMap.size() < maxTableSize) {
				asciiMap.put(asciiMap.size() + 1, string + newString.charAt(0));
			}
			string = newString;
		}
		return decodedOutput;

	}

}
