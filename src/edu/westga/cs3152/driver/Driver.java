package edu.westga.cs3152.driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import edu.westga.cs3152.checker.PlagiarismChecker;
import edu.westga.cs3152.model.PathPairSimilarity;

/**
 * Demo of the plagiarism detection program.
 * 
 * @author CS3152
 * @version Fall 2021
 */
public class Driver {

	/**
	 * Demonstrates the use of the plagiarism checker
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		try (Scanner directoryScanner = new Scanner(System.in)) {
			// Get the directory with the files to be checked
			System.out.print("Name of directory to scan: ");
			System.out.flush();
			String directory = directoryScanner.nextLine();
			Path[] paths = Files.list(Paths.get(directory)).toArray(Path[]::new);
			
			// Instantiate and run the plagiarism checker
			PlagiarismChecker checker = new PlagiarismChecker(paths);
			checker.run();
			
			// Print out a plagiarism report
			ArrayList<PathPairSimilarity> similarFiles = checker.getSimilarFiles(30);
			System.out.println("Plagiarism report:");
			for (PathPairSimilarity pair : similarFiles) {
				System.out.println(pair);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
