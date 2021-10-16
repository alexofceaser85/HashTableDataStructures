package edu.westga.cs3152.model;

import java.nio.file.Path;

/**
 * Class PathPairSimilarity
 * 
 * This class extents class PathPair by including the number of shared n-grams.
 * 
 * @author CS3152
 * @version Fall 2021
 */
public class PathPairSimilarity extends PathPair {
	private int similarity;

	/**
	 * Instantiates a new PathPairSimilarity object
	 * 
	 * @pre none
	 * @post getPath1() = path1 and getPath2() = path2 and getSimilarity() =
	 *       similarity
	 * @param path1      the first path in the pair
	 * @param path2      the second path in the pair
	 * @param similarity number of shared n-grams
	 */
	public PathPairSimilarity(Path path1, Path path2, int similarity) {
		super(path1, path2);
		this.similarity = similarity;
	}

	/**
	 * Instantiates a new PathPairSimilarity object
	 * 
	 * @pre none
	 * @post getPath1() = pair.getPath1() and getPath2() = pair.getPath2() and
	 *       getSimilarity() = similarity
	 * @param pair       the pair of paths
	 * @param similarity number of shared n-grams
	 */
	public PathPairSimilarity(PathPair pair, int similarity) {
		super(pair.getPath1(), pair.getPath2());
		this.similarity = similarity;
	}
	
	/**
	 * Gets the similarity
	 * 
	 * @pre none
	 * @post none
	 * @return the similarity
	 */
	public int getSimilarity() {
		return this.similarity;
	}
	
	/**
	 * Sets the similarity
	 * 
	 * @pre none
	 * @post getSimilarity() = similarity
	 * @param similarity the new similarity value
	 */
	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}

	@Override
	public String toString() {
		return this.similarity + " similarities: " + this.getPath1() + " and " + this.getPath2();
	}

	@Override
	public int compareTo(PathPair other) {
		return ((PathPairSimilarity) other).similarity - this.similarity;
	}
}
