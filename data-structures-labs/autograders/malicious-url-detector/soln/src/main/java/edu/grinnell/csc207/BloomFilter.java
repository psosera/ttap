package edu.grinnell.csc207;

import java.util.BitSet;
import java.util.List;
import java.util.function.Function;

/**
 * A Bloom Filter is a probabilistic data structure that efficiently tests
 * set membership with the possibility of false positives.
 */
public class BloomFilter<T> {
    private BitSet bits;
    private List<Function<T, Integer>> hashes;

    /**
     * Constructs a new Bloom Filter with the given number of bits and hash functions.
     * @param numBits the number of bits utilized in the filter
     * @param hashes the list of hash functions utilized by the filter
     */
    public BloomFilter(int numBits, List<Function<T, Integer>> hashes) {
        this.bits = new BitSet(numBits);
        this.hashes = hashes;
    }

    /** @param item the item to add to the Bloom Filter */
    public void add(T item) {
        for (Function<T, Integer> hash : hashes) {
            int index = Math.abs(hash.apply(item)) % bits.size();
            bits.set(index);
        }
    }

    /**
     * @param item the item to check for membership in the Bloom filter
     * @return true if the item is (possibly) in the Bloom filter and false if
     * it is definitely not in the filter.
     */
    public boolean contains(T item) {
        for (Function<T, Integer> hash : hashes) {
            int index = Math.abs(hash.apply(item)) % bits.size();
            if (!bits.get(index)) {
                return false;
            }
        }
        return true;
    }
}
