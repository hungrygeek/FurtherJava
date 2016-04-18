package cse584;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Aho_Corasick {
	
	public String[] pattern;
	public Trie pTrie;
	
	
	public Aho_Corasick (String[] p) {
		this.pattern = p;
		this.pTrie = Aho_Corasick.construcTrie(p);
		for (int i =0 ; i<this.pattern.length ; i++ ) {
			this.pTrie.searchNode(pattern[i]).output.add(i);
		}
		
		Queue<TrieNode> temp_q = new LinkedList<TrieNode>();
		if (Aho_Corasick.gotoFunction(this.pTrie.root, 'A') != null) {
			temp_q.add(Aho_Corasick.gotoFunction(this.pTrie.root, 'A'));
			Aho_Corasick.gotoFunction(this.pTrie.root, 'A').fNode = this.pTrie.root;
			//System.out.print('a');
		} 
		if (Aho_Corasick.gotoFunction(this.pTrie.root, 'C') != null) {
			temp_q.add(Aho_Corasick.gotoFunction(this.pTrie.root, 'C'));
			Aho_Corasick.gotoFunction(this.pTrie.root, 'C').fNode = this.pTrie.root;
			//System.out.print('a');
		} 
		if (Aho_Corasick.gotoFunction(this.pTrie.root, 'G') != null) {
			temp_q.add(Aho_Corasick.gotoFunction(this.pTrie.root, 'G'));
			Aho_Corasick.gotoFunction(this.pTrie.root, 'G').fNode = this.pTrie.root;
			//System.out.print('a');
		} 
		if (Aho_Corasick.gotoFunction(this.pTrie.root, 'T') != null) {
			temp_q.add(Aho_Corasick.gotoFunction(this.pTrie.root, 'T'));
			Aho_Corasick.gotoFunction(this.pTrie.root, 'T').fNode = this.pTrie.root;
			//System.out.print('a');
		} 
				
		
		while (!temp_q.isEmpty()) {
			TrieNode r = temp_q.poll();
			//temp_q.remove(0);
			if (Aho_Corasick.gotoFunction(r, 'A') != null) {
				temp_q.add(Aho_Corasick.gotoFunction(r, 'A'));
				TrieNode state1 = r.fNode;
				System.out.print('a');
				while (Aho_Corasick.gotoFunction(state1, 'A') == null){
					state1 = state1.fNode;
				}
				Aho_Corasick.gotoFunction(r, 'A').fNode = Aho_Corasick.gotoFunction(state1, 'A');
				Aho_Corasick.gotoFunction(r, 'A').output.addAll(Aho_Corasick.gotoFunction(r, 'A').fNode.output);
			}
			if (Aho_Corasick.gotoFunction(r, 'C') != null) {
				temp_q.add(Aho_Corasick.gotoFunction(r, 'C'));
				TrieNode state1 = r.fNode;
				//System.out.print('a');
				while (Aho_Corasick.gotoFunction(state1, 'C') == null){
					state1 = state1.fNode;
				}
				Aho_Corasick.gotoFunction(r, 'C').fNode = Aho_Corasick.gotoFunction(state1, 'C');
				Aho_Corasick.gotoFunction(r, 'C').output.addAll(Aho_Corasick.gotoFunction(r, 'C').fNode.output);
			}
			if (Aho_Corasick.gotoFunction(r, 'G') != null) {
				temp_q.add(Aho_Corasick.gotoFunction(r, 'G'));
				TrieNode state1 = r.fNode;
				System.out.print('a');
				while (Aho_Corasick.gotoFunction(state1, 'G') == null){
					state1 = state1.fNode;
				}
				Aho_Corasick.gotoFunction(r, 'G').fNode = Aho_Corasick.gotoFunction(state1, 'G');
				Aho_Corasick.gotoFunction(r, 'G').output.addAll(Aho_Corasick.gotoFunction(r, 'G').fNode.output);
			}
			if (Aho_Corasick.gotoFunction(r, 'T') != null) {
				temp_q.add(Aho_Corasick.gotoFunction(r, 'T'));
				TrieNode state1 = r.fNode;
				System.out.print('a');
				while (Aho_Corasick.gotoFunction(state1, 'T') == null){
					state1 = state1.fNode;
				}
				Aho_Corasick.gotoFunction(r, 'T').fNode = Aho_Corasick.gotoFunction(state1, 'T');
				Aho_Corasick.gotoFunction(r, 'T').output.addAll(Aho_Corasick.gotoFunction(r, 'T').fNode.output);
			}
			
		}
		
		
		
	}
	
	public static Trie construcTrie (String[] pattern) {
		Trie pattern_trie = new Trie();
		for (int i = 0 ; i < pattern.length ; i++ ) {
			pattern_trie.insert(pattern[i]);  
		}
		return pattern_trie;
	}
	
	public static TrieNode gotoFunction (TrieNode a , char c) {
		if (a.isRoot) {
			if (a.children.get(c) == null ) {
				return a;
			}
			return a.children.get(c);
		}
		return a.children.get(c);
	}	
	
	
	public ArrayList<int[]> aho_c ( String text ) {
		char[] s = text.toCharArray();
		ArrayList<int[]> result = new ArrayList<int[]>();
				
		//Trie pattern_Trie = Aho_Corasick.construcTrie(pattern);
		TrieNode state = this.pTrie.root;
		for (int i = 0 ; i < s.length ; i ++ ) {
			while ( Aho_Corasick.gotoFunction( state , s[i]) == null ) {
				state = state.fNode;
			}
			state = Aho_Corasick.gotoFunction(state, s[i]);
			if ( state.output != null ) {
				for (int pID : state.output) {
					int[] temp = {i,pID};
					result.add(temp);
				}
			}			
		}
		return result; 
		
	} 
	
	public static void main (String[] args) {
		String[] input = {"A","AC"};
		Aho_Corasick test = new Aho_Corasick(input);
		ArrayList<int[]> result = test.aho_c("ACTA");
		System.out.print(result);
	}
	
	
	
	
}
