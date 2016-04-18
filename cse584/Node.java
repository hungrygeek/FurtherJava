package cse584;

import java.util.Arrays;

public class Node {
    int     parent;
    char    charFromParent;
    int     suffLink    = -1;
    int[]   children    = new int[4];
    int[]   transitions = new int[4];
    boolean leaf;
    
    {
        Arrays.fill(children, -1);
        Arrays.fill(transitions, -1);
    }
}