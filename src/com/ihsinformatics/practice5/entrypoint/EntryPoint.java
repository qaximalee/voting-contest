package com.ihsinformatics.practice5.entrypoint;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryPoint {

	public static void main(String[] args) {

		FileGenerator generator = new FileGenerator();
		InputReader getInputFromFiles = new InputReader();

		List<Map<String, Integer>> votingTable = getInputFromFiles.getVotingData();
		String[] juryCountry = getInputFromFiles.getCountries()
				.toArray(new String[getInputFromFiles.getCountries().size()]);
		if (getInputFromFiles.lifeButton) {
			int[] votingResults = new int[juryCountry.length];

			// Printing All Data On Console
			for (int i = 0; i < votingTable.size(); i++) {
				System.out.println("VOTING JURY OF " + juryCountry[i]);
				Map<String, Integer> map = votingTable.get(i);
				map.forEach((k, v) -> {
					System.out.println(k + "\t" + v);
				});

				// Print Each Country Vote on a file
				generator.outputVoteFile(juryCountry[i], map, juryCountry);
			}

			votingResults = getResults(votingTable, juryCountry);
			// Arrays.sort(votingResults);
			int counter = 0;
			Map<String, Integer> gettingResultsInMap = new HashMap<>();
			for (int i : votingResults) {
				gettingResultsInMap.put(juryCountry[counter], i);
				System.out.println("Vote: " + "\t" + juryCountry[counter++] + "\t" + i);
			}

			String key = Collections.max(gettingResultsInMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue))
					.getKey();

			generator.generateResultFile(key, gettingResultsInMap, votingTable, juryCountry);
		} else {
			System.out.println("THERE IS SOMETHING WRONG");
		}
	}

	public static int[] getResults(List<Map<String, Integer>> voteData, String[] countries) {
		int[] results = new int[countries.length];

		for (int i = 0; i < voteData.size(); i++) {
			results[i] = voteResultForSpecificCountry(voteData, countries[i]);
		}

		return results;
	}

	private static int voteResultForSpecificCountry(List<Map<String, Integer>> contestentCountry, String country) {
		// TODO Auto-generated method stub
		int result = 0;

		for (int i = 0; i < contestentCountry.size(); i++) {
			result += contestentCountry.get(i).get(country);
		}

		return result;
	}
}
