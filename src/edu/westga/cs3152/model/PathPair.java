package edu.westga.cs3152.model;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * Class PathPair
 * 
 * This class represents a pair of file paths.
 * 
 * @author Peter Ljunglöf
 * @author Nick Smallbone
 * @version CS3152 - Fall 2021
 */
public class PathPair implements Comparable<PathPair> {
	private final Path path1;
	private final Path path2;

	/**
	 * Instantiates a new pair of paths
	 * 
	 * @pre none
	 * @post getPath1() = path1 and getPath2() = path2
	 * @param path1 the first path in the pair
	 * @param path2 the second path in the pair
	 */
	public PathPair(Path path1, Path path2) {
		this.path1 = path1;
		this.path2 = path2;
	}

	/**
	 * Gets the first path in this pair
	 * 
	 * @pre none
	 * @post none
	 * @return the first path
	 */
	public Path getPath1() {
		return this.path1;
	}
	
	/**
	 * Gets the second path in this pair
	 * 
	 * @pre none
	 * @post none
	 * @return the second path
	 */
	public Path getPath2() {
		return this.path2;
	}
	
	@Override
	public String toString() {
		return this.path1 + " and " + this.path2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		PathPair pair = (PathPair) obj;
		return this.path1 == pair.path1 && this.path2 == pair.path2;
	}

	@Override
	public int compareTo(PathPair other) {
		return Comparator.comparing((PathPair pair) -> pair.path1).thenComparing(pair -> pair.path2).compare(this,
				other);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.path1.hashCode();
		result = prime * result + this.path2.hashCode();
		return result;
	}
}
