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
	boolean lifeCheck = true;

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
							System.out.println("| " + str[0] + " can't give vote to own country. |");
							System.out.println("========================================");
							lifeCheck = false;
							break;
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

				// Checking whether the list has only five votes
				if (totalVote.size() == 5) {
					for (int value : totalVote) {

						// Values Should be fulfil below conditions
						if ((value >= 1 && value <= 4) || value == 6) {

							// This list is for checking whether the jury country give same vote to more
							// than one country
							List<Integer> checkForDuplicateValue = new ArrayList<>();
							for (int voteValue : totalVote) {
								if (!checkForDuplicateValue.contains(voteValue))
									checkForDuplicateValue.add(voteValue);
							}

							// Now checking above list that if it hasn't 5 votes
							if (checkForDuplicateValue.size() != 5) {
								System.out.println("===================================================");
								System.out.println("|  You can't give same votes to multiple Country |");
								System.out.println("===================================================");
								lifeCheck = false;
								break;
							}
						} else {
							System.out.println("===================================================");
							System.out.println("|  You have to give same vote as required in document |");
							System.out.println("===================================================");
							lifeCheck = false;
							break;
						}
					}
				} else {
					System.out.println("==================================");
					System.out.println("|  You have to vote 5 countries. |");
					System.out.println("==================================");
					lifeCheck = false;
					break;
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
