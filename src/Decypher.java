import java.io.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class Decypher {
    String cypherFile;
    String outputFile;
    String dictionaryFile;
    static ArrayList<String> dictionary;



    public Decypher(String cypherFile, String outputFile, String dictionaryFile) {
        this.cypherFile = cypherFile;
        this.outputFile = outputFile;
        this.dictionaryFile = dictionaryFile;
    }


    private static ArrayList<String> dictionary(String dictionaryFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));
        ArrayList<String> words = new ArrayList<>();
        String word = br.readLine();
        while (word != null) {
            words.add(word);
            word = br.readLine();
        }
        br.close();
        return words;
    }


    public static String decipher(String cipherFile, String outputFile, String dictionaryFile) throws IOException {
        dictionary = dictionary(dictionaryFile);
        String crypt = "";
        int matchedWords;
        int buffer = 0;
        int[] key = new int[2];
        String[] words;


        for(int j = 1; j < 128; j++) {
            for(int i = 1; i < 128; i++) {
                if (Affine.validateInts(i, j)) {
                    crypt = Decrypt.decrypt(cipherFile, outputFile, "" + i, "" + j);
                    words = crypt.toLowerCase().split("\\W+");
                    matchedWords = 0;
                    for (String word: words) {
                        if (dictionary.contains(word)) {
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
