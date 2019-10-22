package com.ihsinformatics.practice5.entrypoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FetchVoteFiles {

	/*
	 * This method returns all path of files in a directory
	 * 
	 * @param String directory path in which the file is present
	 * 
	 * @param String extension of specific file type (i.e .java)
	 */
	public List<String> fetchAllFiles(String directoryPath, String extension) {

		List<String> files = null;

		try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {

			files = walk.map(x -> x.toString()).filter(f -> f.endsWith(extension)).collect(Collectors.toList());

			files.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}
}
