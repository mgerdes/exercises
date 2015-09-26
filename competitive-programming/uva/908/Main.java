import java.util.*;

/* DisjointSets.java */

/**
 *  A disjoint sets ADT.  Performs union-by-rank and path compression.
 *  Implemented using arrays.  There is no error checking whatsoever.
 *  By adding your own error-checking, you might save yourself a lot of time
 *  finding bugs in your application code for Project 3 and Homework 9.
 *  Without error-checking, expect bad things to happen if you try to unite
 *  two elements that are not roots of their respective sets, or are not
 *  distinct.
 *
 *  Elements are represented by ints, numbered from zero.
 *
 *  @author Mark Allen Weiss
 **/

class DisjointSets {

  private int[] array;

  /**
   *  Construct a disjoint sets object.
   *
   *  @param numElements the initial number of elements--also the initial
   *  number of disjoint sets, since every element is initially in its own set.
   **/
  public DisjointSets(int numElements) {
    array = new int [numElements];
    for (int i = 0; i < array.length; i++) {
      array[i] = -1;
    }
  }

  /**
   *  union() unites two disjoint sets into a single set.  A union-by-rank
   *  heuristic is used to choose the new root.  This method will corrupt
   *  the data structure if root1 and root2 are not roots of their respective
   *  sets, or if they're identical.
   *
   *  @param root1 the root of the first set.
   *  @param root2 the root of the other set.
   **/
  public void union(int root1, int root2) {
    if (array[root2] < array[root1]) {
      array[root1] = root2;             // root2 is taller; make root2 new root
    } else {
      if (array[root1] == array[root2]) {
        array[root1]--;            // Both trees same height; new one is taller
      }
      array[root2] = root1;       // root1 equal or taller; make root1 new root
    }
  }

  /**
   *  find() finds the (int) name of the set containing a given element.
   *  Performs path compression along the way.
   *
   *  @param x the element sought.
   *  @return the set containing x.
   **/
  public int find(int x) {
    if (array[x] < 0) {
      return x;                         // x is the root of the tree; return it
    } else {
      // Find out who the root is; compress path by making the root x's parent.
      array[x] = find(array[x]);
      return array[x];                                       // Return the root
    }
  }

  /**
   *  main() is test code.  All the find()s on the same output line should be
   *  identical.
   **/
}

class Edge implements Comparable<Edge> {
	int source;
	int dest;
	int cost;
	
	Edge(int source, int dest, int cost) {
		this.source = source;
		this.dest = dest;
		this.cost = cost;
	}

	@Override
	public int compareTo(Edge e) {
		return this.cost - e.cost;
	}
}

class Main {

	static PriorityQueue<Edge> q = new PriorityQueue<Edge>();
	static DisjointSets s;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		while (input.hasNextInt()) {

			q.clear();

			int n = input.nextInt();

			s = new DisjointSets(n + 1);

			//find original cost

			int originalCost = 0;

			for (int i = 0; i < n - 1; i++) {
				input.nextInt();
				input.nextInt();
				originalCost += input.nextInt();
			}

			System.out.println(originalCost);

			int k = input.nextInt();

			for (int i = 0; i < k; i++) {
				q.add(new Edge(input.nextInt(), input.nextInt(), input.nextInt()));
			}

			int m = input.nextInt();

			for (int i = 0; i < m; i++) {
				q.add(new Edge(input.nextInt(), input.nextInt(), input.nextInt()));
			}

			System.out.println(kruskal());

			if (input.hasNextInt()) System.out.println();

			//if (input.hasNextInt()) System.out.println();

		}

	}

	static int kruskal() {

		int cost = 0;

		while (!q.isEmpty()) {

			Edge current = q.poll();

			int vertex1 = current.source;
			int vertex2 = current.dest;

			if (s.find(vertex1) != s.find(vertex2)) {

				s.union(s.find(vertex1), s.find(vertex2));
				cost += current.cost;

			}

		}
		
		return cost;

	}

}
