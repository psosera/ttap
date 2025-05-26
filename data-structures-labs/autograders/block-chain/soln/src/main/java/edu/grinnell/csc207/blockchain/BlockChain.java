package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {
    private static class Node {
        public Block data;
        public Node next;

        public Node (Block data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node first;
    private Node last;
    public int totalCash;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        totalCash = initial;
        first = new Node(new Block(0, initial, null));
        last  = first;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        return new Block(last.data.getNum() + 1, amount, last.data.getHash());
    }

    public int getSize() {
        return last.data.getNum() + 1;
    }

    public void append(Block blk) {
        if (!blk.getPrevHash().equals(last.data.getHash())) {
            throw new IllegalArgumentException();
        } else {
            Node n = new Node(blk);
            last.next = n;
            last = n;
        }
    }

    public boolean removeLast() {
        if (last.data.getNum() != 0) {
            Node cur = first;
            while (cur.next.next != null) {
                cur = cur.next;
            }
            last = cur;
            last.next = null;
            return true;
        } else {
            return false;
        }
    }

    public Hash getHash() {
        return last.data.getHash();
    }

    public boolean isValidBlockChain() {
        Node cur = first;
        int ab = cur.data.getAmount();
        int bb = 0;
        while (cur.next != null) {
            cur = cur.next;
            int delta = cur.data.getAmount();
            ab += delta;
            bb -= delta;
            if (ab < 0 || bb < 0) {
                return false;
            }
        }
        return true;
    }

    public void printBalances() {
        Node cur = first;
        int ab = cur.data.getAmount();
        int bb = 0;
        while (cur.next != null) {
            cur = cur.next;
            int delta = cur.data.getAmount();
            ab += delta;
            bb -= delta;
        }
        System.out.printf("Alice: %d, Bob: %d\n", ab, bb);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        Node cur = first;
        buf.append(cur.data);
        while (cur.next != null) {
            cur = cur.next;
            buf.append("\n");
            buf.append(cur.data);
        }
        return buf.toString();
    }

}
