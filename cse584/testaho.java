package cse584;

import java.util.*;

public class testaho {

	static class Trie {

		Trie[] next;
		Trie failLink;
		ArrayList<Integer> out;
		int val;

		Trie() {

			next = new Trie[125];
			out = new ArrayList<Integer>();
			val = 0;

		}

	}

	static Trie constructTrie(Trie tmp, String s, int d, int nm) {

		if (tmp == null) {
			tmp = new Trie();
			tmp.val = 2;
		}

		if (s.length() == d) {
			tmp.out.add(nm);
			return tmp;
		}

		char ch = s.charAt(d);

		tmp.next[ch] = constructTrie(tmp.next[ch], s, d + 1, nm);
		return tmp;

	}

	public static void main(String[] args) {
		int t;
		Scanner in = new Scanner(System.in);
		t = in.nextInt();
		String text;

		for (; t-- > 0;) {

			text = in.next();

			int m = in.nextInt();

			String[] pattern = new String[m];

			for (int i = 0; i < m; i++) {

				pattern[i] = in.next();

			}

			// Construct AC Automata

			Trie root = new Trie();
			root.val = 49; // indicator of root so fail can be deepened

			for (int i = 0; i < m; i++) {
				root = constructTrie(root, pattern[i], 0, i);

			}

			Queue<Trie> q = new LinkedList<Trie>();

			for (int i = 1; i < 125; i++) {

				if (root.next[i] != null && root.next[i].val > 0) {

					root.next[i].failLink= root;
					q.offer(root.next[i]);
				}

				else {

					// no path from root node stays there
					root.next[i] = root;

				}
			}

			// bfs contrsuction for failure link

			while (!q.isEmpty()) {

				Trie val1 = q.poll();

				for (int i = 1; i < 125; i++) {
					Trie val = val1;
					if (val.next[i] != null && (val.next[i].val > 0)) {

						q.offer(val.next[i]);
						Trie f = val.failLink;

						// fall through until victory always!

						while (f == null || (f.next[i] != null && f.next[i].val == 0) || f.next[i] == null) {
							f = f.failLink;

						}	

 

						val.next[i].failLink = f.next[i];
						if (f.next[i].out.size() > 0) {

							for ( int j = 0 ; j <f.next[i].out.size() ; j++) {
								val.next[i].out.add(f.next[i].out.get(j));
							}

						}

					}

				}

			}

			int[] res = new int[m];

			// Search Text using Automata

			for (int i = 0; i < text.length(); i++) {

				int c = (text.charAt(i));

				// fall through failure link
				while ((root.next[c] != null && (root.next[c].val == 0)) || (root.next[c] == null)) {

					root = root.failLink;

				}

				root = root.next[c];

				// Hopefully pattern is there

				for (int j = 0; j < root.out.size(); j++) {
					res[root.out.get(j)] = 1;
				}

			}

			for (int i = 0; i < m; i++)
				if (res[i] > 0) {
					System.out.println("y");
				}
				else {
					System.out.println("n");
				}

		}
		in.close();

	}
}


