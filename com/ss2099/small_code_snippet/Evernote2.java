package com.ss2099.small_code_snippet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Evernote2 {
	
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k1).compareTo(map.get(k2));
				if (compare == 0) 
					return 1;
				else 
					return compare;
			}
		};
 
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
	
	
	public static String[] word_count(String[] input,int n){
		SortedMap<String,Integer> output = new TreeMap<String,Integer>();
		for (int i= 1;i<input.length;i++){
			if(!output.containsKey(input[i])) {
				 output.put(input[i],1);
			}
			else {
				 output.put(input[i], output.get(input[i])+1);
			}
		}
		Map<String, Integer> sortedMap = sortByValues(output);
		String[] outputarray = (String[]) sortedMap.keySet().toArray();
		return outputarray;
	}
}
