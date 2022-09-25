import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;

/**
 * Decription class.
 *
 * @version 1.0
 * @author Chingiz Rajabli
 */
public class Decrypt {

    int a;
    int b;

    /**
     * default constructor for the decription class.
     *
     * @param a the first key
     * @param b the second key
     */
    public Decrypt(int a, int b) {
        this.a = a;
        this.b = b;
    }


    /**
     * finds the mod inverse of the a.
     *
     * @param a find the mod inverse of the a
     * @return the mod of a
     */
    int modInverse(int a) {
        for (int i = 1; i < 128; i++) {
            if (((a % 128) * (i % 128)) % 128 == 1) {
                return i;
            }
        }
        return 1;
    }

    /**
     * decrypt the given letter.
     *
     * @param m the given ascii value of the letter
     * @return decrypted version of the letter
     */
    public int decryptLetter(int m) {
        int result = (modInverse(a) *(m - b)) % 128;
        if (result < 0) {
            result += 128;
        }
        return result;
    }


    /**
     * decrypt the whole file and write the decrypted String into the new file.
     *
     * @param fileName encrypted file
     * @param newFileName  decrypted file
     * @param x the key a in string form for the arguments
     * @param y the key b in string form for the arguments
     * @return decrypted String of the file
     * @throws IOException if the file doesnt exist
     */
    public static String decrypt(String fileName, String newFileName, String x, String y) throws IOException {
        String crypt = "";
        int a = parseInt(x);
        int b = parseInt(y);
        if (!Affine.validateInts(a, b)) {
            return String.format("The key pair (%d, %d) is invalid, please select another key.", a, b);
        }
        Decrypt decrypter = new Decrypt(a, b);
        File file = new File(newFileName);
        file.createNewFile();
        FileWriter fw = new FileWriter(file);

        int i;
        try {
            FileReader fr = new FileReader(fileName);
            while ((i = fr.read()) != -1) {
                crypt += (char) decrypter.decryptLetter(i);
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }

        fw.write(crypt);
        fw.close();
        return crypt;

    }

    /**
     * decrypt the encyption but only with the input as a String not as a file, and return the String.
     * @param encryption encrypted String
     * @param a the first key a
     * @param b the first key b
     * @return decrypted String
     */
    public static String decryptString(String encryption, int a, int b) {
        String crypt = "";
        if (!Affine.validateInts(a, b)) {
            return String.format("The key pair (%d, %d) is invalid, please select another key.", a, b);
        }
        Decrypt decrypter = new Decrypt(a, b);

        for (int i = 0; i < encryption.length(); i++) {
            crypt += (char) decrypter.decryptLetter(encryption.charAt(i));
        }


        return crypt;

    }
}
