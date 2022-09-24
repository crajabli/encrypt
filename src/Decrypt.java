import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Decrypt {

    int a;
    int b;

    public Decrypt(int a, int b) {
        this.a = a;
        this.b = b;
    }

    int modInverse(int a) {
        for (int i = 1; i < 128; i++) {
            if (((a % 128) * (i % 128)) % 128 == 1) {
                return i;
            }
        }
        return 1;
    }

    public int decryptLetter(int m) {
        int result = (modInverse(a) *(m - b)) % 128;
        if (result < 0) {
            result += 128;
        }
        return result;
    }


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
