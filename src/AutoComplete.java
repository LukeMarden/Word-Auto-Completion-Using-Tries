import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;

public class AutoComplete {
    private DictionaryFinder df;
    private AutoCompletionTrie trie;

    /**
     * The classes constructor
     */
    public AutoComplete() {
        this.df = new DictionaryFinder();
        this.trie = new AutoCompletionTrie();
    }

    /**
     * This populates the trie with all the words in the dictionary finder hashmap and then finds the key node
     * and assigns the amount of times the word occurs to it
     */
    public void populateTrie() {
        for (String string : this.df.getDictionary().keySet()) {
            this.trie.add(string);
            this.trie.getLast(string).setFrequency(this.df.getDictionary().get(string));
        }
    }

    /**
     * This populates the hashmap for the dictionary finder and tallies the amount each word comes up
     * @param file this is the file that the words come from
     * @throws FileNotFoundException
     */
    public void setDictionaryFinder(String file) throws FileNotFoundException {
        ArrayList<String> in=DictionaryFinder.readWordsFromCSV(file);
        this.df.setIn(in);
        this.df.formDictionary();
    }

    /**
     * This finds all words that come after the query then finds the probabilty of each word by dividing number of
     * times the word comes up by the total number of words possible within that query
     * @param prefix The query prefix
     * @return probabilties followed by the word
     */
    public TreeMap<Double, String> probabilityWords(String prefix) {
        TreeMap<Double, String> probabiltyWords = new TreeMap<Double, String>(Collections.reverseOrder());
        AutoCompletionTrie subTrie = trie.getSubTrie(prefix);
        TreeMap<String, Integer> words = subTrie.getAllWords(subTrie.getRoot(),"");
        Double totalWords = 0.0;
        for (String string : words.keySet()) {
            totalWords += words.get(string);
            
        }
        for (String string : words.keySet()){
            probabiltyWords.put((words.get(string)/totalWords), (prefix +string));
        }
        return probabiltyWords;
    }

    /**
     * Returns all the probabilties
     * @param probabiltyWords The Treemap containing the words and the probabilities
     * @return The probabilties
     */
    public List<Double> getProbabilities(TreeMap<Double, String> probabiltyWords) {
        return new ArrayList(probabiltyWords.keySet());
    }

    /**
     * Returns all the possible words matching that query
     * @param probabiltyWords The Treemap containing the words and the probabilities
     * @return all words
     */
    public List<String> getValues(TreeMap<Double, String> probabiltyWords) {
        return new ArrayList(probabiltyWords.values());
    }

    /**
     * reads all the queries from the file specified
     * @param file the file
     * @return all the queries
     * @throws FileNotFoundException
     */
    public ArrayList<String> readQueries(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
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
     * Saves the 3 most probable to a file specified
     * @param file the file to save to
     * @param probabiltyWords All the words sorted in order of probability
     * @param prefix The query prefix
     * @throws IOException
     */
    public void saveToFile(String file, TreeMap<Double, String> probabiltyWords, String prefix) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
//        int i =0;
        int max = 0;
        if (probabiltyWords.size() > 3) {
            max = 3;
        }
        else {
            max = probabiltyWords.size();
        }
        printWriter.print(prefix + ", ");
        for (int i = 0;i<max;i++) {
            printWriter.print(probabiltyWords.values().toArray()[i] + ", " +
                    probabiltyWords.keySet().toArray()[i] + ", ");
        }
        printWriter.println();
        printWriter.close();
    }

    /**
     * Used to show functionality
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        AutoComplete autoComplete = new AutoComplete();
        autoComplete.setDictionaryFinder("TextFiles/lotr.csv");
        autoComplete.populateTrie();

        ArrayList<String> queries = autoComplete.readQueries("TextFiles/lotrQueries.csv");
        for (int i=0;i<queries.size();i++) {
            TreeMap<Double, String> probabiltyWords = autoComplete.probabilityWords(queries.get(i));
            List<Double> getProbabilities = autoComplete.getProbabilities(probabiltyWords);
            List<String> getValues = autoComplete.getValues(probabiltyWords);
            for (int j = 0; j<getProbabilities.size();j++) {
                System.out.println(getValues.get(j) + ", " + getProbabilities.get(j) + "\n");
            }
            autoComplete.saveToFile("TextFiles/lotrAutoCompleted", probabiltyWords, queries.get(i));
        }






    }




}
