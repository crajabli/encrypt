import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Encrypt {
    int a;
    int b;
    public Encrypt(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int encryptLetter(int m) {
        return ((a * m) + b) % 128;
    }


    public static String encrypt(String fileName, String newFileName, String x, String y) throws IOException {
        String crypt = "";
        int a = parseInt(x);
        int b = parseInt(y);
        if (!Affine.validateInts(a, b)) {
            return String.format("The key pair (%d, %d) is invalid, please select another key.", a, b);
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
