package com.ihsinformatics.practice5.entrypoint;

/*
 * This Class is getting all data required by the program to do processing
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*@author qasim.ali@ihsinformatics.com*/

public class InputReader {

	List<Map<String, Integer>> votingData = new ArrayList<>();
	List<String> countries = new ArrayList<>();
	boolean lifeButton = true;

	/*
	 * This method will run first in the program which will return all data from all
	 * Jury's Files
	 * 
	 * @return List<Map<String, Integer>>
	 */
	public List<Map<String, Integer>> getVotingData() {
		FetchVoteFiles fetchingFiles = new FetchVoteFiles();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the directory path in which voting files are present.");
		String directoryPath = scan.nextLine();
		System.out.println("Please provide the file extension (i.e .txt)");
		String fileExtension = scan.nextLine();

		// Voting Files List
		List<String> filesPaths = fetchingFiles.fetchAllFiles(directoryPath, fileExtension);

		for (String filePath : filesPaths) {

			File file = new File(filePath);

			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));

				String st;
				Map<String, Integer> voteData = new HashMap<>();
				String juryCountry = "";
				List<Integer> totalVote = new ArrayList<>();
				int counter = 0;
				while ((st = br.readLine()) != null) {
					String str[] = st.split("\\W+");

					int countryVote = Integer.parseInt(str[2]);

					if (str[0].equals(str[1])) {
						if (countryVote != 0) {
							System.out.println("========================================");
							System.out.println("| " + str[0] + " can't give vote to his country. |");
							System.out.println("========================================");
							lifeButton = false;
						}
					}
					totalVote.add(countryVote);
					voteData.put(str[1], new Integer(str[2]));
					juryCountry = str[0];
					counter++;
				}
				while (totalVote.contains(new Integer(0))) {
					totalVote.remove(new Integer(0));
				}
				if (totalVote.size() > 5) {
					System.out.println("==================================================");
					System.out.println("|  You can't give vote to more than 5 countries. |");
					System.out.println("==================================================");
				}
				votingData.add(voteData);
				countries.add(juryCountry);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setCountries(countries);
		return votingData;
	}

	// setter for countries
	public List<String> getCountries() {
		return countries;
	}

	// getter for countries
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
}
