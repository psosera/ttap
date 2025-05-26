package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.blockchain.Block;

public class BlockTests {
    @Test
    public void makeValidBlocks() throws NoSuchAlgorithmException {
        var blk = new Block(0, 300, null);
        assertEquals(0, blk.getNum(), "1st block number");
        assertEquals(300, blk.getAmount(), "1st block amount (300)");
        assertTrue(blk.getHash().isValid(), "1st block hash valid?");

        var blk2 = new Block(1, -150, blk.getHash());
        assertEquals(1, blk2.getNum(), "2nd block number");
        assertEquals(-150, blk2.getAmount(), "2nd block amount (-150)");
        assertTrue(blk2.getHash().isValid(), "2nd block hash valid?");

        var blk3 = new Block(2, 25, blk2.getHash());
        assertEquals(2, blk3.getNum(), "3rd block number");
        assertEquals(25, blk3.getAmount(), "3nd block amount (-150)");
        assertTrue(blk3.getHash().isValid(), "2nd block hash valid?");
    }
}
