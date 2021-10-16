package edu.westga.cs3152.checker;

import edu.westga.cs3152.model.Ngram;
import edu.westga.cs3152.model.PathNgrams;
import edu.westga.cs3152.model.PathPair;
import edu.westga.cs3152.model.PathPairSimilarity;
import edu.westga.cs3152.util.Stopwatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Class PlagiarismChecker
 * 
 * The plagiarism detection program.
 * 
 * @author CS3152
 * @version Fall 2021
 */
public class PlagiarismChecker {
	private int ngramLength;
	private Path[] paths;
	private boolean checkCompleted;
	private Hashtable<PathPair, Integer> similarity;

	/**
	 * Instantiates a new PlagiarismDetector instance
	 * 
	 * @pre paths != null
	 * @post the checker has been initialized to check the the specified files using
	 *       the n-gram length of 5
	 * @param paths the array of files that need to be checked
	 */
	public PlagiarismChecker(Path[] paths) {
		this.paths = paths;
		Arrays.sort(this.paths);
		this.checkCompleted = false;
		this.ngramLength = 5;
	}

	/**
	 * Sets the n-gram length, i.e. the checker uses length-grams to determine the
	 * similarity among files
	 * 
	 * @pre none
	 * @post getNgramLength() = length
	 * @param length the new n-gram length
	 */
	public void setNgramLength(int length) {
		if (length != this.ngramLength) {
			this.ngramLength = length;
			this.checkCompleted = false;
		}
	}

	/**
	 * Gets the n-gram length
	 * 
	 * @pre none
	 * @post none
	 * @return the n-gram length used by the checker
	 */
	public int getNgramLength() {
		return this.ngramLength;
	}

	/**
	 * Detects the level of similarity between any two files
	 * 
	 * @pre none
	 * @post similarities among files has been established
	 */
	public void run() {
		if (this.checkCompleted) {
			return;
		}
		try {
			Stopwatch stopwatch = new Stopwatch();
			ArrayList<PathNgrams> filesWithNgrams = this.readPaths();
			stopwatch.finished("Reading all input files");

			Hashtable<Ngram, ArrayList<PathNgrams>> nGramsPresentInFiles = new Hashtable<Ngram, ArrayList<PathNgrams>>(); 
			
			for (PathNgrams pathNgram : filesWithNgrams) {
				for (Ngram nGram : pathNgram.getNgrams()) {
					if (nGramsPresentInFiles.containsKey(nGram)) {
						nGramsPresentInFiles.get(nGram).add(pathNgram);
					} else {
						ArrayList<PathNgrams> nGramPaths = new ArrayList<PathNgrams>();
						nGramPaths.add(pathNgram);
						nGramsPresentInFiles.put(nGram, nGramPaths);
					}
				}
			}
			
			this.findSimilarity(filesWithNgrams, nGramsPresentInFiles);
			stopwatch.finished("Computing similarity scores");

			this.checkCompleted = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	


	/**
	 * Reads in each file and chop it into n-grams. returns a list of distinct
	 * n-grams for each file.
	 * 
	 * @return list of files with their n-grams
	 * @throws IOException if a file in paths cannot be read
	 */
	private ArrayList<PathNgrams> readPaths() throws IOException {
		ArrayList<PathNgrams> filesWithNgrams = new ArrayList<PathNgrams>();
		for (Path path : this.paths) {
			String contents = new String(Files.readAllBytes(path));
			Ngram[] ngrams = Ngram.ngrams(contents, this.ngramLength);
			ngrams = Arrays.stream(ngrams).distinct().toArray(Ngram[]::new);
			filesWithNgrams.add(new PathNgrams(path, ngrams));
		}
		return filesWithNgrams;
	}
	
	/**
	 * Counts how many n-grams each pair of files has in common.
	 * 
	 * @param filesWithNgrams list of files with their n-grams
	 */
	private void findSimilarity(ArrayList<PathNgrams> filesWithNgrams, Hashtable<Ngram, ArrayList<PathNgrams>> ngramsToFiles) {
		this.similarity = new Hashtable<PathPair, Integer>();
		
		HashSet<Ngram> previousNGrams = new HashSet<Ngram>();

		for (PathNgrams pathNgram : filesWithNgrams) {
			for (Ngram nGram : pathNgram.getNgrams()) {
				if (previousNGrams.contains(nGram)) {
					continue;
				} else {
					previousNGrams.add(nGram);
				}
				
				ArrayList<PathNgrams> nGramFiles = ngramsToFiles.get(nGram);
				
				for (PathNgrams firstNGramFile : nGramFiles) {
				
					for (PathNgrams secondNGramFile : nGramFiles) {
						if (firstNGramFile.getPath().equals(secondNGramFile.getPath())) {
							continue;
						}
						
						PathPair pair = new PathPair(firstNGramFile.getPath(), secondNGramFile.getPath());
						if (!this.similarity.containsKey(pair)) {
							this.similarity.put(pair, 1);
						} else {
							this.similarity.put(pair, this.similarity.get(pair) + 1);
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the pairs of files that have at least minSimularity many n-grams in
	 * common sorted in descending order of similarity. For two given files a and b,
	 * there is at most one of the two possible pairs (a, b) or (b, a) included.
	 * There are no pairs consisting of the same file twice included.
	 * 
	 * @pre the method run has been executed previously
	 * @post none
	 * @param minSimilarity minimum number shared n-grams
	 * @return all pair of files that share at least minSimilarity many n-grams;
	 *         returns null if the precondition is not met
	 */
	public ArrayList<PathPairSimilarity> getSimilarFiles(int minSimilarity) {
		if (!this.checkCompleted) {
			return null;
		}

		ArrayList<PathPairSimilarity> similarFiles = new ArrayList<>();
		for (PathPair pair : this.similarity.keySet()) {
			if (this.similarity.get(pair) < minSimilarity) {
				continue;
			}
			if (pair.getPath1().compareTo(pair.getPath2()) <= 0) {
				continue;
			}
			similarFiles.add(new PathPairSimilarity(pair.getPath1(), pair.getPath2(), this.similarity.get(pair)));
		}
		Collections.sort(similarFiles);
		return similarFiles;
	}
}
