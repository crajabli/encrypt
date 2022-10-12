import java.io.IOException;

/**
 * the main class of the whole project.
 *
 * @version 1.0
 * @author Chingiz Rajabli
 */
public class Affine {

    /**
     * find gcd of the two numbers.
     *
     * @param a key a
     * @param b key b
     * @return the gcd of the two provided numbers
     */
    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a < b) {
            return gcd(b, a);
        }
        return gcd(b, a % b);
    }

    /**
     * validate the integers and see if they can be used as keys or not.
     *
     * @param a the key a
     * @param b the key b
     * @return the boolean
     */
    public static boolean validateInts(int a, int b) {
        if (a <= 1 || a > 128 || b < 0) {
            return false;
        }
        return gcd(a, 128) == 1;
    }


    /**
     * main method.
     * @param args the arguments for th argument line
     * @throws IOException if the file doesnt exist
     */
    public static void main(String[] args) throws IOException {
        String command = args[0];
        switch (command) {
            case "encrypt":
                Encrypt.encrypt(args[1], args[2], args[3], args[4]);
                break;
            case "decrypt":
                Decrypt.decrypt(args[1], args[2], args[3], args[4]);
                break;
            case "decipher":
                Decypher.decipher(args[1], args[2], args[3]);
                break;
            default:
                System.out.println("Wrong command");
                break;
        }
    }
}