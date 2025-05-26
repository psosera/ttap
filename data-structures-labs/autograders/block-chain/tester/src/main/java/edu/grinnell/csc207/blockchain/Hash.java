package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] data;

    /**
     * @param data the byte array wrapped by this hash
     */
    public Hash(byte[] data) {
        this.data = data;
    }

    /**
     * @return the byte array wrapped by this hash
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return true iff the hash is valid (it starts with three zeros)
     */
    public boolean isValid() {
        return data[0] == 0 && data[1] == 0 && data[2] == 0;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (byte b : data) {
            buf.append(String.format("%02x", Byte.toUnsignedInt(b)));
        }
        return buf.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            return Arrays.equals(data, ((Hash) other).data);
        } else {
            return false;
        }
    }
}
