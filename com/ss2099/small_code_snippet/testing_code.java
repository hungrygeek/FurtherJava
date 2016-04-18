package com.ss2099.small_code_snippet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class testing_code {
	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		al.add(1);
		al.add(2);
		al.add(3);
		al.add(4);
		al.add(5);
		Iterator it = al.iterator();
		while (it.hasNext()) {
			int a = (Integer) it.next();
			if(it.hasNext()) {
				a = (Integer) it.next();
				System.out.print(a+"\n");
			}
			else {}
		}
	
	} 
}
