import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class Decypher {
    String cypherFile;
    String outputFile;
    String dictionaryFile;
    int a;
    int b;



    public Decypher(String cypherFile, String outputFile, String dictionaryFile) {
        this.cypherFile = cypherFile;
        this.outputFile = outputFile;
        this.dictionaryFile = dictionaryFile;
    }




    public static boolean isInFile(String wordExamine, String dictionaryFile) throws FileNotFoundException {
        try {
            FileReader fr = new FileReader(dictionaryFile);
            BufferedReader bufferedReader = new BufferedReader(fr);
            String word = bufferedReader.readLine();
            while (word != null) {
                if (word.contains(wordExamine)) {
                    return true;
                }
                word = bufferedReader.readLine();
            }
            bufferedReader.close();
            fr.close();

        } catch (IOException ie) {
            System.out.println("File doesnt exist");
        }


        return false;

    }



    public static String decipher(String cipherFile, String outputFile, String dictionaryFile) throws IOException {
        String crypt = "";
        int matchedWords;
        int buffer = 0;
        int[] key = new int[2];
        String[] words;
        String cipher = Files.readString(Path.of(cipherFile));


        for(int j = 1; j < 128; j++) {
            for(int i = 1; i < 128; i++) {
                if (Affine.validateInts(i, j)) {
                    crypt = Decrypt.decryptString(cipher, i, j);
                    words = crypt.toLowerCase().split("\\W+");
                    matchedWords = 0;
                    for (String word: words) {
                        if (isInFile(word, dictionaryFile)) {
                            System.out.println(word);
                           matchedWords++;
                        }
                    }
                    if (matchedWords > buffer) {
                        key[0] = i;
                        key[1] = j;
                        buffer = matchedWords;
                    }
                }

            }
        }

        crypt = String.format("%d %d\nDECIPHERED MESSAGE:\n", key[0], key[1]) +
                Decrypt.decrypt(cipherFile, outputFile, "" + key[0], "" + key[1]);

        return crypt;


    }


}
