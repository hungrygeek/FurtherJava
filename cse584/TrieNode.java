package cse584;

import java.util.ArrayList;
import java.util.HashMap;

public class TrieNode {
    char c;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean isLeaf;
    boolean isRoot = false;
    TrieNode fNode = null;
    ArrayList<Integer> output = new ArrayList<Integer>();
 
    public TrieNode() {}
 
    public TrieNode(char c){
        this.c = c;
    }
}