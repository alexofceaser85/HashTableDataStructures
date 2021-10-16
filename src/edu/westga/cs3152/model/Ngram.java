package edu.westga.cs3152.model;

import java.util.stream.Stream;

/**
 * Class Ngram
 * 
 * An object of class Ngram represents the subsequence of the 'tokens' array
 * starting at index 'offset' and having length 'length':
 * 
 * tokens[offset], tokens[offset+1], ..., tokens[offset+1-length].
 * 
 * This design allows us to reduce memory use by sharing the tokens array
 * between all n-grams coming from the same input string.
 * 
 * @author Peter Ljunglöf
 * @author Nick Smallbone
 * @version CS3152 - Fall 2021
 */
public class Ngram implements Comparable<Ngram> {
	private String[] tokens;
	private int offset;
	private int length;

	/**
	 * Instantiates an n-gram
	 * 
	 * @param tokens string of tokens containing the n-gram
	 * @param offset index at with the n-gram starts
	 * @param length the length of the n-gram
	 */
	public Ngram(String[] tokens, int offset, int length) {
		this.tokens = tokens;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Returns all n-grams of a given input string.
	 * 
	 * @param input the input string
	 * @param length the length of the n-grams to be returned
	 * @return an array of all n-grams
	 */
	public static Ngram[] ngrams(String input, int length) {
		String[] tokens = Stream.of(input.split("\\W")).filter(str -> !str.equals("")).map(str -> str.toLowerCase())
				.toArray(String[]::new);

		int count = tokens.length - length + 1;
		if (count < 0) {
			count = 0;
		}
		Ngram[] ngrams = new Ngram[count];
		for (int idx = 0; idx < count; idx++) {
			ngrams[idx] = new Ngram(tokens, idx, length);
		}
		return ngrams;
	}

	/**
	 * Gets the length of this n-gram
	 * 
	 * @return the length
	 */
	public int length() {
		return this.length;
	}

	/**
	 * Returns the token at the specified index in this n-gram
	 * 
	 * @param idx the index
	 * @return the token at the specified index
	 */
	public String at(int idx) {
		return this.tokens[idx + this.offset];
	}

	@Override
	public int compareTo(Ngram other) {
		if (this.length != other.length) {
			return this.length - other.length;
		}
		for (int idx = 0; idx < this.length; idx++) {
			int wordDiff = this.at(idx).compareTo(other.at(idx));
			if (wordDiff != 0) {
				return wordDiff;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		return this.compareTo((Ngram) obj) == 0;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		for (int idx = 0; idx < this.length; idx++) {
			hash = hash * 37 + this.at(idx).hashCode();
		}
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		if (this.length != 0) {
			result.append(this.at(0));
			for (int idx = 1; idx < this.length; idx++) {
				result.append('/');
				result.append(this.at(idx));
			}
		}
		return result.toString();
	}
}
