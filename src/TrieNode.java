/*
https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
suggests using array for offspring for faster performance
 */
public class TrieNode {
    private TrieNode[] offspring;
    private boolean isKey;
    private char s;
    /**
     * method for making an offspring
     * @param s is the character to set
     */
    public TrieNode(char s) {
        this.offspring = new TrieNode[26];
        this.isKey = false;
        this.s = s;
    }
    /**
     * method for making the root of the trie
     */
    public TrieNode() {
        this.offspring = new TrieNode[26];
        this.isKey = false;
        this.s = Character.MIN_VALUE;
    }

    /**
     * A method for duplicating a TrieNode
     * @param node the node to be duplicated
     */
    public TrieNode(TrieNode node) {
        this.offspring = node.getAllOffspring();
        this.isKey = node.getIsKey();
        this.s = node.getChar();
    }

    /**
     * Returns the offsping node at a character
     * @param x the character to find the node at
     * @return the node the be returned
     */
    public TrieNode getOffspring(char x) {
        return offspring[(int)x-97];
    }

    /**
     * Returns the offspring array
     * @return the array
     */
    public TrieNode[] getAllOffspring() {
        return this.offspring;
    }

    /**
     * Sets the offspring at a character
     * @param x The char to set at the node
     * @return true if was null, false if it was already assigned
     */
    public Boolean setOffspring(char x) {
        if (this.offspring[(int)x-97] == null) {
            this.offspring[(int)x-97] = new TrieNode(x);
            return true;
        }
        return false;
    }

    /**
     * Sets the offspring to a certain node
     * @param x the node to it as
     * @return True if the node was null, false if it wasnt
     */
    public Boolean setOffSpring(TrieNode x) {
        if (this.offspring[(int)x.getChar()-97] == null) {
            this.offspring[(int)x.getChar()-97] = new TrieNode(x.getChar());
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



}
