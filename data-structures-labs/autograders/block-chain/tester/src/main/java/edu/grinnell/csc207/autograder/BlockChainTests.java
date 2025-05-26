package edu.grinnell.csc207.autograder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.blockchain.Block;
import edu.grinnell.csc207.blockchain.BlockChain;
import edu.grinnell.csc207.blockchain.Hash;

public class BlockChainTests {

    @Test
    public void emptyBlockChainTest() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(350);
        assertEquals(1, chain.getSize());
        assertTrue(chain.isValidBlockChain());
    }

    @Test
    public void validBlockChainTest() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(350);
        assertEquals(1, chain.getSize());
        Block blk = chain.mine(-100);
        chain.append(blk);
        assertEquals(2, chain.getSize());
        assertTrue(chain.isValidBlockChain());
        blk = chain.mine(50);
        chain.append(blk);
        assertEquals(3, chain.getSize());
        assertTrue(chain.isValidBlockChain());
    }

    @Test
    public void invalidBlockChainTest() throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(250);
        assertEquals(1, chain.getSize());
        Block blk = chain.mine(-300);
        chain.append(blk);
        assertEquals(2, chain.getSize());
        assertFalse(chain.isValidBlockChain());
    }
}
