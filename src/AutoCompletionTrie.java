import java.util.*;

public class AutoCompletionTrie {

    private AutoCompletionTrieNode root;
    public static void main(String[] args) {
        AutoCompletionTrie test = new AutoCompletionTrie();
        test.add("cheers");
        test.add("cheese");
        test.add("chat");
        test.add("cat");
        test.add("bat");
        test.add("cheers");
        test.add("batch");
        System.out.println("test.getLast(\"cheers\") = " + test.getLast("cheers"));
        if (test.contains("chat")) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        System.out.println("test.outputBreadthFirstSearch() = " + test.outputBreadthFirstSearch());
        System.out.println("test.outputDepthFirstSearch() = " + test.outputDepthFirstSearch());
        AutoCompletionTrie subTrie = test.getSubTrie("ch");
        System.out.println("subTrie.root.getOffspring('h').getChar() = " + subTrie.getRoot().getChar());
        char[] test1 = null;

        TreeMap<String, Integer> words = test.getAllWords(test.getRoot(), "");
//        List<String> strings = test.getAllWords(test.getRoot(), "");
        for(int i=0;i<words.size();i++) {
            System.out.println("words.keySet().toArray()[i] = " + words.keySet().toArray()[i]);
            System.out.println("words.values().toArray()[i] = " + words.values().toArray()[i]);
        }


    }

    /**
     * Gets the last node in a word
     * @param string the string to find the last node of
     * @return the last node
     */
    public AutoCompletionTrieNode getLast(String string){
        char[] strToChar = string.toCharArray();
        AutoCompletionTrieNode last = this.root;
        for (int i = 0; i<strToChar.length; i++){
            last = last.getOffspring(strToChar[i]);
        }
        return last;
    }

    /**
     * Gets the root of the trie
     * @return the root
     */
    public AutoCompletionTrieNode getRoot() {
        return this.root;
    }

    /**
     * Sets the root of the trie
     * @param root the node to set the root as
     */
    public void setRoot(AutoCompletionTrieNode root) {
        this.root = root;
    }

    /**
     * The constructor for a Trie
     */
    public AutoCompletionTrie() {
        this.root = new AutoCompletionTrieNode();
    }

    /**
     * Adds a string to the trie
     * **** Also increases the frequency if the word is already in the trie
     * @param str the string to add
     */
    public void add(String str) {
        Boolean nullVisited = false;
        AutoCompletionTrieNode temp = root;
        AutoCompletionTrieNode next = null;
        char[] stringToCharArray = str.toCharArray();
        for (char c: stringToCharArray) {
            if (nullVisited == false) {
                next = temp.getOffspring(c);
                if (next == null) {
                    next = new AutoCompletionTrieNode(c);
                    temp.setOffspring(next.getChar());
                    nullVisited = true;
                }
                temp = temp.getOffspring(c);
            }
            else {
                next = new AutoCompletionTrieNode(c);
                temp.setOffspring(next.getChar());
                temp = temp.getOffspring(c);
            }


        }
        temp.setIsKey(true);
        temp.setFrequency(temp.getFrequency()+1);
    }

    /**
     * Checks if a string is in the trie
     * @param str the string to check for
     * @return true if it is in the trie, false if it is not
     */
    public Boolean contains(String str) {
        AutoCompletionTrieNode temp = root;
        char[] stringToCharArray = str.toCharArray();
        for (int i=0;i<stringToCharArray.length;i++) {
            temp=temp.getOffspring(stringToCharArray[i]);
            if (temp==null) {
                return false;
            }
        }
        return true;
    }

    /**
     * performs a breadth first search
     * @return a string consisting of the search result
     */
    public String outputBreadthFirstSearch() {
        String result = "";
        Queue<AutoCompletionTrieNode> queue = new LinkedList<>();
        queue.add(this.getRoot());
        while(!queue.isEmpty()) {
            AutoCompletionTrieNode temp = queue.remove();
            if (!temp.equals(this.getRoot())) {
                result += temp.getChar();
            }
            for (AutoCompletionTrieNode node : temp.getAllOffspring()) {
                if (node!=null) {
                    queue.add(node);
                }
            }
        }
        return result;
    }

    /**
     * Performs a depth first search
     * @return the search result in the form of a string
     */
    public String outputDepthFirstSearch() {
        String result = "";
        Stack<AutoCompletionTrieNode> stack = new Stack<>();
        stack.push(this.getRoot());
        while(!stack.isEmpty()) {
            AutoCompletionTrieNode temp = stack.pop();
            if (!temp.equals(this.getRoot())) {
                result += temp.getChar();
            }
            for (int i = temp.getAllOffspring().length-1;i>=0;i--) {
                if (temp.getOffspring((char)(i+97)) != null) {
                    stack.push(temp.getOffspring((char)(i+97)));
                }
            }
        }
        return result;
    }

    /**
     * Makes a subtrie after the prefix
     * @param prefix the prefix to find the subtrie after
     * @return the subtrie
     */
    public AutoCompletionTrie getSubTrie(String prefix) {
        AutoCompletionTrieNode temp = this.root;
        char[] stringToCharArray = prefix.toCharArray();
        for (int i=0;i<stringToCharArray.length;i++) {
            temp = temp.getOffspring(stringToCharArray[i]);
        }
        AutoCompletionTrie subTrie = new AutoCompletionTrie();
        subTrie.setRoot(new AutoCompletionTrieNode(temp));
        subTrie.getRoot().setChar('\0');
//        subTrie.getRoot().setIsKey(false);
        return subTrie;
    }

    /**
     * gets all the words from a trie
     * @param node the node to start on
     * @param word the word being made
     * @return a list of the words
     */
    public TreeMap<String, Integer> getAllWords(AutoCompletionTrieNode node, String word){
        TreeMap<String, Integer> words = new TreeMap<>();
        Stack<AutoCompletionTrieNode> stack = new Stack<>();
        if (!node.equals(this.getRoot())) {
            word += node.getChar();
        }
        for(AutoCompletionTrieNode trieNode : node.getAllOffspring()){
            if(trieNode!=null) {
                stack.push(trieNode);
            }
        }
        while(!stack.empty()){
            words.putAll(getAllWords(stack.pop(),word));
        }
        if(node.getIsKey()){
            words.put(word, node.getFrequency());
        }
        return words;
    }

}
