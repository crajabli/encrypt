import java.io.IOException;


public class Affine {

    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a < b) {
            return gcd(b, a);
        }
        return gcd(b, a % b);
    }

    public static boolean validateInts(int a, int b) {
        if (a <= 1 || a > 128 || b < 0) {
            return false;
        }
        return gcd(a, 128) == 1;
    }


    public static void main(String[] args) throws IOException {
        String command = args[0];
        switch (command) {
            case "encrypt":
                System.out.println("Encrypt");
                System.out.println(Encrypt.encrypt(args[1], args[2], args[3], args[4]));
                break;
            case "decrypt":
                System.out.println("Decrypt");
                System.out.println(Decrypt.decrypt(args[1], args[2], args[3], args[4]));
                break;
            case "decipher":
                System.out.println("Decipher");
                System.out.println(Decypher.decipher(args[1], args[2], args[3]));
                break;
        }
    }
}