public class Encryption {
    public static void printUsageAndExit() {
        System.out.println("Usage: java Encryption <encode|decode> <text>");
        System.exit(1);
    }

    public static boolean isInvalidText(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLowerCase(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int charToValue(char ch) {
        return (int) (ch - 'a');
    }

    public static char valueToChar(int n) {
        return (char) (n + 'a');
    }

    public static int shiftModdedBy(int k, int n, int m) {
        if (n >= 0) {
            return (k + n) % m;
        } else {
            int subBy = -n % m;
            int ret = k - subBy;
            if (ret < 0) {
                return m + ret;
            } else {
                return ret;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2 ||
            (!args[0].equals("encode") && !args[0].equals("decode")) ||
            isInvalidText(args[1])) {
            printUsageAndExit();
        }

        int k = args[0].equals("encode") ? 1 : -1;
        String text = args[1];

        for (int i = 0; i < 26; i++) {
            char[] shiftedChars = new char[text.length()];
            for (int j = 0; j < text.length(); j++) {
                shiftedChars[j] = valueToChar(
                    shiftModdedBy(charToValue(text.charAt(j)), i * k, 26));
            }
            System.out.println("n = " + i + ": " + new String(shiftedChars));
        }
        System.out.println();
    }
}
