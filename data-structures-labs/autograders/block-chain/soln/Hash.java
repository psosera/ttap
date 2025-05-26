import java.util.Arrays;

public class Hash {
    private byte[] data;

    public Hash(byte[] data) {
        this.data = data;
    }

    public byte[] getData() { return data; }

    public boolean isValid() {
        return data[0] == 0 && data[1] == 0 && data[2] == 0;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (byte b : data) {
            buf.append(String.format("%02x", Byte.toUnsignedInt(b)));
        }
        return buf.toString();
    }

    public boolean equals(Object other) {
        if (other instanceof Hash) {
            return Arrays.equals(data, ((Hash) other).data);
        } else {
            return false;
        }
    }

    // N.B. stub to get rid of warning
    public int hashCode() { return -1; }
}
