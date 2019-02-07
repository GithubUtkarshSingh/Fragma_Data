package com.rd.CsvCoding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FragmaData {

	// function to get top 4 teams
	static void top(int[] counter, int[] top4, int TeamLength, List<String> uniqueTeam, String Year) {
		int max = 0, index, i;

		int[] temp = new int[TeamLength];

		for (int k = 0; k < TeamLength; k++) {
			temp[k] = counter[k];
		}

		for (int j = 0; j < 4; j++) {
			max = temp[0];
			index = 0;
			for (i = 1; i < temp.length; i++) {
				if (max < temp[i]) {
					max = temp[i];
					index = i;
				}
			}
			top4[j] = index;
			temp[index] = Integer.MIN_VALUE;

			System.out.println("Year " + Year + " Team " + uniqueTeam.get(top4[j]) + " Count " + counter[top4[j]]);
		}
		System.out.println("");
	}

	//List total number of fours, sixes, total score with respect to team and year. 
	static void analyzeScore(int[][] deliveriesData, int deliveriesRowCount, int coun, List<String> uniqueTeam,
			String[][] arr) {
		int i, j, k;
		int fours1;
		int sixes1;
		int fours2;
		int sixes2;
		int totalScore1;
		int totalScore2;

		for (i = 1; i < coun; i++) {
			fours1 = 0;
			sixes1 = 0;
			fours2 = 0;
			sixes2 = 0;
			totalScore1 = 0;
			totalScore2 = 0;
			for (j = 1; j < deliveriesRowCount; j++) {
				if (deliveriesData[j][0] == i) {
					if (deliveriesData[j][1] == 1) {
						if (deliveriesData[j][4] == 4) {
							fours1++;
						} else if (deliveriesData[j][4] == 6) {
							sixes1++;
						}
						totalScore1 = totalScore1 + deliveriesData[j][5];
					} else {
						if (deliveriesData[j][4] == 4) {
							fours2++;
						} else if (deliveriesData[j][4] == 6) {
							sixes2++;
						}

						totalScore2 = totalScore2 + deliveriesData[j][5];
					}
					deliveriesData[j][11] = Integer.parseInt(arr[i][0]);
				}
			}
			System.out.println("\nYear: " + arr[i][0] + "\nTeamName: " + arr[i][1] + " , Fours:  " + fours1
					+ " , Sixes: " + sixes1 + " , TotalScore: " + totalScore1);
			System.out.println("TeamName: " + arr[i][2] + " , Fours:  " + fours2 + " , Sixes: " + sixes2
					+ " , TotalScore: " + totalScore2);
		}
	}

	
	 /*Top 10 best economy rate bowler with respect to year who bowled at least 10 overs ​(
			 ​ LEGBYE_RUNS and BYE_RUNS should not be considered for Total Runs Given by a bowler) 
			  */
	static void bestEconomy(List<String> uniqueBowlingTeam, int[][] deliveriesData, int deliveriesRowCount,
			List<String> uniqueYear) {
		int i, k, j = 0;
		int uniqueBowlingTeamLen = uniqueBowlingTeam.size();
		// Array for storing Year economy rate, Bowler Index
		float[][] economyRateDataResult = new float[uniqueBowlingTeamLen][3];
		int[][] economyRateData1 = new int[uniqueBowlingTeamLen][5];
		HashMap<Integer, Integer> economyRateData = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> economyRateDataYear = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> oversBowled = new HashMap<Integer, Integer>();

		int Year;
		for (i = 1; i < uniqueYear.size(); i++) {
			Year = Integer.parseInt(uniqueYear.get(i));
			for (j = 1; j < deliveriesRowCount; j++) {
				if (Year == deliveriesData[j][11]) {
					// key is from uniqueTeamBowler List
					int key = deliveriesData[j][10];

					int value = deliveriesData[j][7] + deliveriesData[j][4] + deliveriesData[j][6]
							+ deliveriesData[j][8] + deliveriesData[j][9];

					value = value + (economyRateData.get(key) == null ? 0 : economyRateData.get(key));

					int NballBowled;
					NballBowled = (oversBowled.get(key) == null ? 0 : oversBowled.get(key)) + 1;
					economyRateData.put(key, value);
					economyRateDataYear.put(key, Year);
					oversBowled.put(key, NballBowled);
				}
			}
			for (j = 0; j < economyRateDataYear.size(); j++) {
				float economyRate;
				if (Year == economyRateDataYear.get(j)) {
					economyRate = oversBowled.get(j) / 6;
					economyRate = economyRateData.get(j) / economyRate;
					if ((oversBowled.get(j) / 6) > 10) {
						economyRateDataResult[j][0] = Year;
						economyRateDataResult[j][1] = j;
						economyRateDataResult[j][2] = economyRate;
					}
				}
			}
			economyRateData.clear();
			oversBowled.clear();

		}
		//function programming with lambda expression
	Arrays.sort(economyRateDataResult,(a,b)-> {return Double.compare(a[2], b[2]);});
		
		int top10;
		for (i = 1; i < uniqueYear.size(); i++) {
			Year = Integer.parseInt(uniqueYear.get(i));
			top10 = 1;
			System.out.print("******************Year: " + Year + "***********************\n");
			for (j = economyRateDataResult.length - 1; j >= 0; j--) {
				if (Year == economyRateDataResult[j][0] && top10 <= 10) {
					top10++;
					int index = Math.round(economyRateDataResult[j][1]);
					System.out.println("Year: " + Year + " , Player: " + uniqueBowlingTeam.get(index) + " , Economy: "
							+ economyRateDataResult[j][2]);
				}
			}
		}
	}
	
}