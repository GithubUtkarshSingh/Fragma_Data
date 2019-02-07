package com.rd.CsvCoding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestMain {
	public static void main(String[] args) 
	{
	try {	String line;
		String cvsSplitBy = ",";
		int coun;

		int deliveriesRowCount;
		String fileName = "matches.csv";

		String fileNameDeliveries = "deliveries.csv";

		// loading data into two multidimensional Arrays
		InputStream is = new BufferedInputStream(new FileInputStream(fileName));
		InputStream isd = new BufferedInputStream(new FileInputStream(fileNameDeliveries));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int Dcount = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			while ((readChars = isd.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++Dcount;
					}
				}
			}
			coun = (count == 0 && !empty) ? 1 : count;

			deliveriesRowCount = (Dcount == 0 && !empty) ? 1 : Dcount;

		} finally {
			is.close();
			isd.close();
		}

		String[][] arr = new String[coun][7];
		int[][] deliveriesData = new int[deliveriesRowCount][12];

		// Convert String Array to List
		List<String> uniqueTeam = new ArrayList<String>();

		// unique bowling Team
		List<String> uniqueBowlingTeam = new ArrayList<String>();

		// Unique Year
		List<String> uniqueYear = new ArrayList<String>();

		int incrementer = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			BufferedReader brD = new BufferedReader(new FileReader(fileNameDeliveries));

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] cols = line.split(cvsSplitBy);

				arr[incrementer][0] = cols[1];
				arr[incrementer][1] = cols[4];
				arr[incrementer][2] = cols[5];
				arr[incrementer][3] = cols[6];
				arr[incrementer][4] = cols[7];
				arr[incrementer][5] = cols[8];
				arr[incrementer][6] = cols[0];
				incrementer++;

				String kk = cols[4];
				// insert unique team list

				if (!uniqueTeam.contains(cols[4])) {
					uniqueTeam.add(kk);
				}
				// loading unique years
				if (!uniqueYear.contains(cols[1])) {
					uniqueYear.add(cols[1]);
				}
			}
			int iteration = 0;
			incrementer = 1;
			while ((line = brD.readLine()) != null) {
				// use comma as separator
				String[] cols = line.split(cvsSplitBy);

				// skipping headers of csv into array
				if (iteration == 0) {
					iteration++;
					continue;
				} else {
					deliveriesData[incrementer][0] = Integer.parseInt(cols[0]);
					deliveriesData[incrementer][1] = Integer.parseInt(cols[1]);
					deliveriesData[incrementer][2] = Integer.parseInt(cols[4]);
					deliveriesData[incrementer][3] = Integer.parseInt(cols[5]);

					// bats man runs
					deliveriesData[incrementer][4] = Integer.parseInt(cols[13]);
					// total runs
					deliveriesData[incrementer][5] = Integer.parseInt(cols[15]);

					// wide runs
					deliveriesData[incrementer][6] = Integer.parseInt(cols[8]);
					// No Ball Runs
					deliveriesData[incrementer][7] = Integer.parseInt(cols[11]);
					// Penalty runs
					deliveriesData[incrementer][8] = Integer.parseInt(cols[12]);
					// Extra Runs
					deliveriesData[incrementer][9] = Integer.parseInt(cols[14]);

					// System.out.println("Column 0: " + deliveriesData[incrementer][0] + " , Column
					// 1: " + deliveriesData[incrementer][1] + " , Column 4: " +
					// deliveriesData[incrementer][2] + " , Column 5: " +
					// deliveriesData[incrementer][3] + " , Column 13: " +
					// deliveriesData[incrementer][4] + " , Column 15: " +
					// deliveriesData[incrementer][5]);

					// loading unique bowlers list
					String kk = cols[7];
					// insert unique team list

					if (!uniqueBowlingTeam.contains(kk)) {
						uniqueBowlingTeam.add(kk);
					}
					// loading bowling team index
					deliveriesData[incrementer][10] = uniqueBowlingTeam.indexOf(kk);

					incrementer++;

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int i, count = 0;

		int uniqueTeamLen;

		uniqueTeamLen = uniqueTeam.size();

		int[] counter = new int[uniqueTeamLen];

		int[] top4 = new int[5];
		String Year;

		for (int year = 2016; year <= 2017; year++) {
			Year = Integer.toString(year);
			for (i = 1; i < count; i++) {
				String field = "field";
				if (Year.equals(arr[i][0]) && field.equals(arr[i][4])) {
					int index = uniqueTeam.indexOf(arr[i][3]);
					counter[index]++;
				}
			}

			System.out.println("\nFirst Question\n");

			FragmaData.top(counter, top4, uniqueTeamLen, uniqueTeam, Year);
			for (int k = 0; k < uniqueTeamLen; k++) {
				counter[k] = 0;
			}
		}

		System.out.println("Second Question");
		FragmaData.analyzeScore(deliveriesData, deliveriesRowCount, coun, uniqueTeam, arr);

		System.out.println("\nThird Question\n");
		FragmaData.bestEconomy(uniqueBowlingTeam, deliveriesData, deliveriesRowCount, uniqueYear);
	}catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		
	}
	}
}
