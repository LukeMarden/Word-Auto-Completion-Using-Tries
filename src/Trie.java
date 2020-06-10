import java.util.*;

public class Trie {

    private TrieNode root;
    public static void main(String[] args) {
        Trie test = new Trie();
        test.add("cheers");
        test.add("cheese");
        test.add("chat");
        test.add("cat");
        test.add("bat");
        test.add("batch");
        if (test.contains("chat")) {
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
        System.out.println("test.outputBreadthFirstSearch() = " + test.outputBreadthFirstSearch());
        System.out.println("test.outputDepthFirstSearch() = " + test.outputDepthFirstSearch());
        Trie subTrie = test.getSubTrie("ch");
        System.out.println("subTrie.root.getOffspring('h').getChar() = " + subTrie.getRoot().getChar());
        char[] test1 = null;

        List<String> strings = test.getAllWords(test.getRoot(), "");
        for(int i=0;i<strings.size();i++) {
            System.out.println("strings.get(i) = " + strings.get(i));
        }

        
    }

    /**
     * Gets the root of the trie
     * @return the root
     */
    public TrieNode getRoot() {
        return this.root;
    }

    /**
     * Sets the root of the trie
     * @param root the node to set the root as
     */
    public void setRoot(TrieNode root) {
        this.root = root;
    }

    /**
     * The constructor for a Trie
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Adds a string to the trie
     * @param str the string to add
     */
    public void add(String str) {
        Boolean nullVisited = false;
        TrieNode temp = root;
        TrieNode next = null;
        char[] stringToCharArray = str.toCharArray();
        for (char c: stringToCharArray) {
            if (nullVisited == false) {
                next = temp.getOffspring(c);
                if (next == null) {
                    next = new TrieNode(c);
                    temp.setOffspring(next.getChar());
                    nullVisited = true;
                }
                temp = temp.getOffspring(c);
            }
            else {
                next = new TrieNode(c);
                temp.setOffspring(next.getChar());
                temp = temp.getOffspring(c);
            }


        }
        temp.setIsKey(true);
    }

    /**
     * Checks if a string is in the trie
     * @param str the string to check for
     * @return true if it is in the trie, false if it is not
     */
    public Boolean contains(String str) {
        TrieNode temp = root;
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
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(this.getRoot());
        while(!queue.isEmpty()) {
            TrieNode temp = queue.remove();
            if (!temp.equals(this.getRoot())) {
                result += temp.getChar();
            }
            for (TrieNode node : temp.getAllOffspring()) {
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
        Stack<TrieNode> stack = new Stack<>();
        stack.push(this.getRoot());
        while(!stack.isEmpty()) {
            TrieNode temp = stack.pop();
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
    public Trie getSubTrie(String prefix) {
        TrieNode temp = this.root;
        char[] stringToCharArray = prefix.toCharArray();
        for (int i=0;i<stringToCharArray.length;i++) {
            temp = temp.getOffspring(stringToCharArray[i]);
        }
        Trie subTrie = new Trie();
        subTrie.setRoot(new TrieNode(temp));
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
    public List<String> getAllWords(TrieNode node, String word){
        List<String> list = new ArrayList<>();
        Stack<TrieNode> stack = new Stack<>();
        if (!node.equals(this.getRoot())) {
            word += node.getChar();
        }
        for(TrieNode trieNode : node.getAllOffspring()){
            if(trieNode!=null) {
                stack.push(trieNode);
            }
        }
        while(!stack.empty()){
            list.addAll(getAllWords(stack.pop(),word));
        }
        if(node.getIsKey()){
            list.add(word);
        }
        return list;
    }

}
