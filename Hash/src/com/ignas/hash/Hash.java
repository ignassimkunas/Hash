package com.ignas.hash;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class Hash{
	static void test1() throws IOException{
		File oneSymbol1 = new File("onesymbol1.txt");
		File oneSymbol2 = new File("onesymbol2.txt");
		File different1 = new File("different1.txt");
		File different2 = new File("different2.txt");
		File onediff1 = new File("onediff1.txt");
		File onediff2 = new File("onediff2.txt");
		File empty = new File("empty.txt");
		try {
			String lotsOfDifferent1 = "", lotsOfDifferent2 = "", oneDifferent1 = "", oneDifferent2 = "", emptyString = "";
			Scanner sc = new Scanner(oneSymbol1);
			char c1 = sc.next().charAt(0);
			System.out.println("One symbol file 1: \n" + toHash(String.valueOf(c1)));
			sc.close();
			sc = new Scanner(oneSymbol2);
			c1 = sc.next().charAt(0);
			System.out.println("\nOne symbol file 2: \n" + toHash(String.valueOf(c1)));
			sc.close();
			sc = new Scanner(different1);
			while (sc.hasNextLine()) {
				lotsOfDifferent1 += sc.nextLine();
			}
			System.out.println("\nLots of different symbols 1: \n" + toHash(lotsOfDifferent1));
			sc.close();
			sc = new Scanner(different2);
			while (sc.hasNextLine()) {
				lotsOfDifferent2 += sc.nextLine();
			}
			System.out.println("\nLots of different symbols 2: \n" + toHash(lotsOfDifferent2));
			sc.close();
			sc = new Scanner(onediff1);
			while (sc.hasNextLine()) {
				oneDifferent1 += sc.nextLine();
			}
			System.out.println("\nOne different symbol 1: \n" + toHash(oneDifferent1));
			sc.close();
			sc = new Scanner(onediff2);
			while (sc.hasNextLine()) {
				oneDifferent2 += sc.nextLine();
			}
			System.out.println("\nOne different symbol 2: \n" + toHash(oneDifferent2));
			sc.close();
			sc = new Scanner(empty);
			while (sc.hasNextLine()) {
				emptyString += sc.nextLine();
			}
			System.out.println("\nEmpty file: \n" + toHash(emptyString));	
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	static void test2() throws IOException{
		double start, end;
		File konstit = new File("konstitucija.txt");
		FileWriter fw = new FileWriter(new File("out.txt"));
		Scanner sc = new Scanner(konstit);
		double sum = 0;
		while (sc.hasNextLine()) {
			start = System.currentTimeMillis();
			toHash(sc.nextLine());
			end = System.currentTimeMillis();
			sum += end - start;
		}
		System.out.println(sum + " milisekundžių");
	}
	static void test3() throws IOException {
		Scanner sc = new Scanner(new File("pairs.txt"));
		String line;
		int counter = 0;
		boolean isMatch = false;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			counter++;
			if (toHash(line.split(" ")[0]) == toHash(line.split(" ")[1])) {
				System.out.println("Match on line " + counter);
				isMatch = true;
			}
		}
		if (!isMatch) {
			System.out.println("No matches found");
		}
		sc.close();	
	}
	static double calculateBinaryDiff (String s1, String s2) {
		double ress = 0;
		if (s1.length() == s2.length()) {
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) == s2.charAt(i)){
					ress++;
				}
			}
			return (ress / s1.length()) * 100;
		}
		else {
			System.out.println("Different bit lenghts");
		}
		return ress;
	}
	static void test4() throws IOException{
		Scanner sc = new Scanner(new File("pairsonediff.txt"));
		String line, firstString, secondString, binary1, binary2;
		ArrayList<Double> diff = new ArrayList<Double>();
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			firstString = line.split(" ")[0];
			secondString = line.split(" ")[1];
			binary1 = new BigInteger(firstString.getBytes()).toString(2);
			binary2 = new BigInteger(secondString.getBytes()).toString(2);
			diff.add(calculateBinaryDiff(binary1, binary2));
		}
		Collections.sort(diff);
		double sum = 0, avg;
		for (double x : diff) {
			sum += x;
		}
		avg = sum / diff.size();
		System.out.println("Min " + diff.get(0) + " " + "\nMax " +diff.get(diff.size() - 1) + "\nAverage " + avg);
	}
	static String generateString(int targetLength) {
		int leftLimit = 33;
	    int rightLimit = 126;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetLength);
	    for (int i = 0; i < targetLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    return buffer.toString();
	    
	}
	static String fromBeginingToEnd(String input) {
		char[] digit = input.toCharArray();
		char first = digit[0];
		for(int i = 0; i < digit.length -1; i++) {
			digit[i] = digit[i+1];
		}
		digit[digit.length - 1] = first;
		input = new String(digit);
		return input;
	}
	
	static String toHash(String input){
		String binaryString = "", originalBinary;
		// ascii kodus į binary ir viską sudeda
		for (char c : input.toCharArray()){
			binaryString += Integer.toString((int)c, 2);
		}
		binaryString += "1";
		int counter = 1;
		//jei trumpesnis, prideda tą patį, kas antrą kartą apversdamas.
		if (binaryString.length() < 78) {
			while(binaryString.length() < 78) {
				if (counter % 2 == 0) {
					binaryString += binaryString;
				}
				else {
					StringBuilder temp = new StringBuilder();
					temp.append(binaryString);
					binaryString += temp.reverse().toString();
				}
				counter++;
			}
		}
		//jei ilgesnis, sutrumpina
		if (binaryString.length() > 78){
			binaryString = binaryString.substring(0, 78);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(binaryString);
		//apverčia ir apkeičia pradžioje esančius bitus su gale esančiais
		binaryString = builder.reverse().toString();
		for (int i = 0; i < 10; i++) {
			binaryString = fromBeginingToEnd(binaryString);
		}
		BigInteger result = new BigInteger(binaryString, 2);
		String hashString = result.toString(16);
		if (hashString.length() < 20) {
			hashString += input.charAt(0);
		}
		return hashString;
	}
	
	public static void main(String args[])throws IOException{
		test4();
		if (args.length > 0) {
			Scanner sc = new Scanner(new FileInputStream(args[0]));
			String inputString = "";
			while (sc.hasNextLine()) {
				inputString += sc.nextLine();
			}
			System.out.println(toHash(inputString));
		}
		else {
			System.out.println(toHash("Blockchain"));
		}
		
	}
}