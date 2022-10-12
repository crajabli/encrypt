import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;

/**
 * Encypt class for the file encryption.
 *
 * @version 1.0
 * @author Chingiz Rajabli
 */
public class Encrypt {
    int a;
    int b;

    /**
     * default constructor for the Encrypt.
     *
     * @param a key a
     * @param b key b
     */
    public Encrypt(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * encrypt one letter at a time.
     *
     * @param m the letter to be encrypted
     * @return encrypted value
     */
    public int encryptLetter(int m) {
        return ((a * m) + b) % 128;
    }


    /**
     * Encrypt the given file.
     *
     * @param fileName standard file
     * @param newFileName encypted file
     * @param x key a in the String form for the arguments in the command line
     * @param y key b in the String form for the arguments in the command line
     * @return the encrypted file
     * @throws IOException if the standard file doesnt exist
     */
    public static String encrypt(String fileName, String newFileName, String x, String y) throws IOException {
        String crypt = "";
        int a = parseInt(x);
        int b = parseInt(y);
        if (!Affine.validateInts(a, b)) {
            return String.format("The key pair ({%d}, {%d}) is invalid, please select another key.", a, b);
        }
        Encrypt encrypter = new Encrypt(a, b);
        File file = new File(newFileName);
        file.createNewFile();
        FileWriter fw = new FileWriter(file);

        int i;
        try {
            FileReader fr = new FileReader(fileName);
            while ((i = fr.read()) != -1) {
                crypt += (char) encrypter.encryptLetter(i);
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }

        fw.write(crypt);
        fw.close();
        return crypt;
    }
}
