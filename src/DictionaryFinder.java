

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.*;

/**
 *
 * @author ajb
 */
public class DictionaryFinder {
    private TreeMap<String, Integer> dictionary;
    /**
     * All the different strings from the input file
     */
    private ArrayList<String> in;

    /**
     * The classes constructor
     */
    public DictionaryFinder(){
        this.dictionary = new TreeMap<>();
        this.in = new ArrayList<>();
    }

    /**
     * Reads all the words in a comma separated text document into an Array
     * @param file
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words=new ArrayList<>();
        String str;
        while(sc.hasNext()){
            str=sc.next();
            str=str.trim();
            str=str.toLowerCase();
            words.add(str);
        }
        return words;
    }

    /**
     * This adds all the words to the treemap and tallies up the amount each word occurs
     */
    public void formDictionary(){
        for (String string: in){
            if (this.dictionary.get(string) == null) {
                this.dictionary.put(string, 1);
            }
            else {
                this.dictionary.put(string, this.dictionary.get(string)+1);
            }
        }
    }

    /**
     * This saves the results into a file specified and is ordered in alphabetical order
     * @param file the file to save to
     * @throws IOException
     */
    public void saveToFile(String file) throws IOException{
        /*
        https://howtodoinjava.com/sort/java-sort-map-by-key/
        treemaps are naturally sorted
         */
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(String string: dictionary.keySet()) {
            printWriter.println(string + ", " + this.dictionary.get(string));
        }
        printWriter.close();

    }

    /**
     * gets the words to be inputted to the treemap
     * @return The list of words
     */
    public ArrayList<String> getIn() {
        return in;
    }

    /**
     * sets the words to be inputted to the treemap
     * @param in the words to be inputted into the treemap
     */
    public void setIn(ArrayList in) {
        this.in = in;
    }

    /**
     * This gets the treemap
     * @return the treemap
     */
    public TreeMap<String, Integer> getDictionary() {
        return dictionary;
    }

    /**
     * Sets the treemap
     * @param dictionary what the treemap is to be set as
     */
    public void setDictionary(TreeMap<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Used for testing
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        DictionaryFinder df=new DictionaryFinder();
        ArrayList<String> in=readWordsFromCSV("TextFiles/lotr.csv");
        df.setIn(in);
        df.formDictionary();
        df.saveToFile("TextFiles/testDictionary2.csv");
    }
    
}
