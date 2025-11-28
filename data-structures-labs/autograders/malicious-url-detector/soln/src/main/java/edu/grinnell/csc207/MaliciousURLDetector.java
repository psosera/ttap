package edu.grinnell.csc207;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * A simple malicious URL detector program that utilizes a Bloom Filter and a
 * dataset of known malicious URLs to efficiently check whether a URL is
 * potentially malicious.
 */
public class MaliciousURLDetector {
    // From: https://www.kaggle.com/datasets/sid321axn/malicious-urls-dataset
    public static final String DATA_PATH = "data/malicious_phish.csv";

    /**
     * Creates a list of <code>num</code> string hash functions utilizing the
     * Murmur3 hashing algorithm.
     * @param num the number of hash functions
     * @return a list of <code>num</code> string hash functions
     */
    public static List<Function<String, Integer>> makeStringHashFunctions(int num) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        List<Function<String, Integer>> ret = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            HashFunction fn = Hashing.murmur3_128(rand.nextInt());
            ret.add(item -> fn.hashString(item, Charset.defaultCharset()).asInt());
        }
        return ret;
    }

    /**
     * @param numBits the number of bits dedicated to the filter
     * @param numHashFunctions the number of hash functions to use
     * @return a Bloom filter for detecting malicious URLs.
     */
    public static BloomFilter<String> makeURLFilter(
            int numBits, int numHashFunctions) throws FileNotFoundException {
        BloomFilter<String> filter = new BloomFilter<>(
            numBits,
            makeStringHashFunctions(numHashFunctions));
        Scanner in = new Scanner(new File(DATA_PATH));
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            String[] tokens = line.split(",");
            if (tokens.length == 2) {
                String url = tokens[0];
                String label = tokens[1];
                if (!label.equals("benign")) {
                    filter.add(url);
                }
            }
        }
        in.close();
        return filter;
    }

    /**
     * The main method for the program.
     * @param args the arguments to the program
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.err.println("Usage: java MaliciousURLDetector <numBits> <numHashFunctions>");
            return;
        }
        int numBits = Integer.parseInt(args[0]);
        int numHashFunctions = Integer.parseInt(args[1]);
        BloomFilter<String> filter = makeURLFilter(numBits, numHashFunctions);
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a URL to check (or \"exit\" to quit):");
        System.out.print("> ");
        String input = in.nextLine().trim();
        while (!input.equals("exit")) {
            if (filter.contains(input)) {
                System.out.println("‼️ The URL is possibly malicious...");
            } else {
                System.out.println("✅ The URL is not known to be malicious...");
            }
            System.out.print("> ");
            input = in.nextLine().trim();
        }
        in.close();
    }
}
