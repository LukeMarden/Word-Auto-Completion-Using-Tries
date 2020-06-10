public class AutoCompletionTrieNode {
    private AutoCompletionTrieNode[] offspring;
    private boolean isKey;
    private char s;
    private int frequency;

    /**
     * method for making an offspring
     * @param s is the character to set
     */
    public AutoCompletionTrieNode(char s) {
        this.offspring = new AutoCompletionTrieNode[26];
        this.isKey = false;
        this.s = s;
        this.frequency = 0;
    }

    /**
     * method for making the root of the trie
     */
    public AutoCompletionTrieNode() {
        this.offspring = new AutoCompletionTrieNode[26];
        this.isKey = false;
        this.s = Character.MIN_VALUE;
        this.frequency = 0;
    }

    /**
     * A method for duplicating a TrieNode
     * @param node the node to be duplicated
     */
    public AutoCompletionTrieNode(AutoCompletionTrieNode node) {
        this.offspring = node.getAllOffspring();
        this.isKey = node.getIsKey();
        this.s = node.getChar();
        this.frequency = node.getFrequency();

    }

    /**
     * Returns the offsping node at a character
     * @param x the character to find the node at
     * @return the node the be returned
     */
    public AutoCompletionTrieNode getOffspring(char x) {
        return offspring[(int)x-97];
    }

    /**
     * Returns the offspring array
     * @return the array
     */
    public AutoCompletionTrieNode[] getAllOffspring() {
        return this.offspring;
    }

    /**
     * Sets the offspring at a character
     * @param x The char to set at the node
     * @return true if was null, false if it was already assigned
     */
    public Boolean setOffspring(char x) {
        if (this.offspring[(int)x-97] == null) {
            this.offspring[(int)x-97] = new AutoCompletionTrieNode(x);
            return true;
        }
        return false;
    }

    /**
     * Sets the offspring to a certain node
     * @param x the node to it as
     * @return True if the node was null, false if it wasnt
     */
    public Boolean setOffSpring(AutoCompletionTrieNode x) {
        if (this.offspring[(int)x.getChar()-97] == null) {
            this.offspring[(int)x.getChar()-97] = new AutoCompletionTrieNode(x.getChar());
            return true;
        }
        return false;
    }

    /**
     * Returns if the node is a key
     * @return true if it is a key, false if it isnt
     */
    public Boolean getIsKey() {
        return isKey;
    }

    /**
     * Sets the nodes key status
     * @param x the status to set it as
     */
    public void setIsKey(Boolean x) {
        isKey = x;
    }

    /**
     * Gets the char of the node
     * @return the char
     */
    public char getChar() {
        return s;
    }

    /**
     * Sets the char at a node
     * @param x the char to set it as
     */
    public void setChar(char x) {
        this.s = x;
    }

    /**
     * gets the frequency of a word
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency of a word
     * @param f1 the frequency to set it as
     */
    public void setFrequency(int f1) {
        this.frequency = f1;
    }



}
