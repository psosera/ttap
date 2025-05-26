package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.blockchain.Hash;

public class HashTests {
    private Hash hash(int... values) {
        byte[] bytes = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            bytes[i] = (byte) values[i];
        }
        return new Hash(bytes);
    }

    @Test
    public void detectsValidHashes() {
        assertTrue(hash(0, 0, 0).isValid(), "[0, 0, 0]");
        assertTrue(hash(0, 0, 0, 0, 0).isValid(), "[0, 0, 0, 0, 0]");
        assertTrue(hash(0, 0, 0, 31, 5, 127, 6).isValid(), "[0, 0, 0, 31, 5, 127, 6]");
    }

    @Test
    public void detectsinValidHashes() {
        assertFalse(hash(1, 0, 0).isValid(), "[1, 0, 0]");
        assertFalse(hash(0, 1, 0).isValid(), "[0, 1, 0]");
        assertFalse(hash(0, 0, 1).isValid(), "[0, 0, 1]");
        assertFalse(hash(1, 1, 0).isValid(), "[1, 1, 0]");
        assertFalse(hash(1, 0, 1).isValid(), "[1, 0, 1]");
        assertFalse(hash(0, 1, 1).isValid(), "[0, 1, 1]");
        assertFalse(hash(1, 1, 1).isValid(), "[1, 1, 1]");
        assertFalse(hash(0, 1, 0, 0, 0).isValid(), "[0, 1, 0, 0, 0]");
        assertFalse(hash(0, 1, 0, 31, 5, 127, 6).isValid(), "[0, 1, 0, 31, 5, 127, 6]");
    }

    @Test
    public void hashToString() {
        assertEquals("0001001f057f06", hash(0, 1, 0, 31, 5, 127, 6).toString());
    }

    @Test
    public void hashEquals() {
        assertTrue(hash(0, 1, 0, 31, 5, 127, 6).equals(hash(0, 1, 0, 31, 5, 127, 6)),
            "[0, 1, 0, 31, 5, 127, 6]");
        assertFalse(hash(0, 1, 2, 3).equals(hash(0, 0, 0)));
    }
}
