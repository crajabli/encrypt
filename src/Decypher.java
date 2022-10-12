import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Decypher class which tries to decipher the encrypted file without provided keys.
 *
 * @version 1.0
 * @author Chingiz Rajabli
 */
public class Decypher {
    String cypherFile;
    String outputFile;
    String dictionaryFile;
    static ArrayList<String> dictionary;


    /**
     * the constructor for the Decypher
     *
     * @param cypherFile encypted file
     * @param outputFile decyphered file with the key compbination
     * @param dictionaryFile the provided dictionary file
     */
    public Decypher(String cypherFile, String outputFile, String dictionaryFile) throws IOException {
        this.cypherFile = cypherFile;
        this.outputFile = outputFile;
        this.dictionaryFile = dictionaryFile;
    }


    /**
     * loads dictionary into the memory for the faster execution.
     *
     * @param dictionaryFile dictionary words
     * @return dictionary in the arraylist form
     * @throws IOException if the file doesnt exist
     */
    private static ArrayList<String> dictionary(String dictionaryFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));
        ArrayList<String> words = new ArrayList<>();
        String word = br.readLine();
        while (word != null) {
            words.add(word.toLowerCase());
            word = br.readLine();
        }
        br.close();
        return words;
    }



    private static boolean dictionaryContains(String word) {
        for (String words: dictionary) {
            if (words.equals(word)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Deciphering function for the decipher class.
     *
     * @param cipherFile the encrypted file
     * @param outputFile decrypted file with the keys
     * @param dictionaryFile the words used to decypher
     * @return the deciphered file with the keys included
     * @throws IOException if the encripted file doesnt exist
     */
    public static String decipher(String cipherFile, String outputFile, String dictionaryFile) throws IOException {
        dictionary = dictionary(dictionaryFile);
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
                        if (words.length > buffer) {
                            if (word.length() >= 4 && dictionaryContains(word)) {
                                System.out.println(word);
                                matchedWords++;
                            }
                        }

                    }
                    if (matchedWords > buffer) {
                        key[0] = i;
                        key[1] = j;
                        buffer = matchedWords;
                        System.out.println("number of the matched words " + matchedWords);
                    }
                }

            }
        }

        crypt = String.format("%d %d\nDECIPHERED MESSAGE:\n", key[0], key[1]) +
                Decrypt.decrypt(cipherFile, outputFile, "" + key[0], "" + key[1]);

        File file = new File(outputFile);
        file.createNewFile();
        FileWriter fw = new FileWriter(file);

        fw.write(crypt);
        fw.close();

        return crypt;


    }


}
