package edu.grinnell.csc207.speedreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordGenerator {

	private final Scanner in;
	private int wordCount;
	private int sentenceCount;

	public WordGenerator(String filename) throws FileNotFoundException {
		this.in = new Scanner(new File(filename));
		this.wordCount = 0;
		this.sentenceCount = 0;
	}

	public boolean hasNext() {
		return in.hasNext();
	}

	private static boolean endsWithPunctuation(String s) {
		return s.endsWith(".")
		    || s.endsWith("!")
		    || s.endsWith("?");
	}

	public String next() {
		String word = in.next();
		this.wordCount += 1;
		this.sentenceCount
		    += endsWithPunctuation(word) ? 1 : 0;
		return word;
	}

	public int getWordCount() {
		return this.wordCount;
	}

	public int getSentenceCount() {
		return this.sentenceCount;
	}
}
