package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.speedreader.WordGenerator;

public class SpeedReaderTests {

    private static class WordGeneratorResult {
      public final int words;
      public final int sentences;

      public WordGeneratorResult(int words, int sentences) {
        this.words = words;
        this.sentences = sentences;
      }

      public boolean equals(Object other) {
        if (other instanceof WordGeneratorResult) {
          WordGeneratorResult otherResult = (WordGeneratorResult) other;
          return this.words == otherResult.words && this.sentences == otherResult.sentences;
        }
        return false;
      }

      public String toString() {
        return String.format("{words: %d, sentences: %d}", words, sentences);
      }
    }

    private static WordGeneratorResult runWordGenerator(String filename) throws FileNotFoundException {
      WordGenerator gen = new WordGenerator(filename);
      while (gen.hasNext()) { gen.next(); }
      return new WordGeneratorResult(gen.getWordCount(), gen.getSentenceCount());
    }

    @Test
    @DisplayName("csc207.txt counts correct?")
    public void csc207Test() throws FileNotFoundException {
      assertEquals(new WordGeneratorResult(90, 4),
        runWordGenerator("autograder-texts/csc207.txt"));
    }

    @Test
    @DisplayName("speed-reading-wikipedia.txt counts correct?")
    public void wikipediaTest() throws FileNotFoundException {
      assertEquals(new WordGeneratorResult(391, 13),
        runWordGenerator("autograder-texts/speed-reading-wikipedia.txt"));
    }

    @Test
    @DisplayName("war-and-peace.txt counts correct?")
    public void warAndPeaceTest() throws FileNotFoundException {
      assertEquals(new WordGeneratorResult(331, 13),
        runWordGenerator("autograder-texts/war-and-peace.txt"));
    }
}
