package edu.westga.cs3152.model;

import java.nio.file.Path;

/**
 * Class PathNgrams
 * 
 * This class stores the n-grams of a file specified by a Path object.
 * 
 * @author Cs3152
 * @version Fall 2021
 */
public class PathNgrams {
	private final Path path;
	private final Ngram[] ngrams;

	/**
	 * Instantiates a new PathNgrams object
	 * 
	 * @pre none
	 * @post getPath() = path and getNgrams() = ngrams
	 * @param path the file
	 * @param ngrams the array with the n-grams of thefile
	 */
	public PathNgrams(Path path, Ngram[] ngrams) {
		this.path = path;
		this.ngrams = ngrams;
	}
	
	/**
	 * Gets the path of the file
	 * 
	 * @pre none
	 * @post none
	 * @return the path
	 */
	public Path getPath() {
		return this.path;
	}

	/**
	 * Gets the n-grams of this file
	 * 
	 * @pre none
	 * @post none
	 * @return the array of n-grams
	 */
	public Ngram[] getNgrams() {
		return this.ngrams;
	}
}
