import java.util.*;

/* 
	Disjoint Sets ADT
	@author Mark Allen Weiss
 */

class DisjointSets {

  private int[] array;

  public DisjointSets(int numElements) {
    array = new int [numElements];
    for (int i = 0; i < array.length; i++) {
      array[i] = -1;
    }
  }

  public void union(int root1, int root2) {
    if (array[root2] < array[root1]) {
      array[root1] = root2;           
    } else {
      if (array[root1] == array[root2]) {
        array[root1]--;            
      }
      array[root2] = root1;     
    }
  }

  public int find(int x) {
    if (array[x] < 0) {
      return x;                         
    } else {
      array[x] = find(array[x]);
      return array[x];                                       
    }
  }

}

class Edge implements Comparable<Edge> {
	int source;
	double sourceX;
	double sourceY;

	int dest;
	double destX;
	double destY;

	double dist;
	
	Edge(int source, double sourceX, double sourceY, int dest, double destX, double destY, double dist) {
		this.source = source;
		this.sourceX = sourceX;
		this.sourceY = sourceY;

		this.dest = dest;
		this.destX = destX;
		this.destY = destY;

		this.dist = dist;
	}

	@Override
	public int compareTo(Edge e) {
		return (this.dist - e.dist >= 0) ? 1 : -1;
	}
}

class Main {
	/*

	1

	3
	1.0 1.0
	2.0 2.0
	2.0 4.0

	*/
	static double[][] freckles;

	static PriorityQueue<Edge> q = new PriorityQueue<Edge>();
	static DisjointSets s;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int n = input.nextInt(); // number of test cases

		for (int i = 0; i < n; i++) {

			q.clear();

			int numOfFreckles = input.nextInt(); 

			freckles = new double[numOfFreckles][2];
			s = new DisjointSets(numOfFreckles);

			for (int j = 0; j < numOfFreckles; j++) {

				double x = input.nextDouble();
				double y = input.nextDouble();

				freckles[j][0] = x;
				freckles[j][1] = y;

			}

			for (int j = 0; j < numOfFreckles; j++) {
				for (int k = j + 1; k < numOfFreckles; k++) {

					double x1 = freckles[j][0];
					double y1 = freckles[j][1];

					double x2 = freckles[k][0];
					double y2 = freckles[k][1];

					double distance = Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 1.0/2);

					q.add(new Edge(j, x1, y1, k, x2, y2, distance));

				}
			}

			System.out.printf("%.2f\n", kruskal());

			if(input.hasNextInt()) System.out.println();

		}
	}

	static double kruskal() {

		double dist = 0;

		while (!q.isEmpty()) {

			Edge current = q.poll();

			int vertex1 = current.source;
			int vertex2 = current.dest;

			if (s.find(vertex1) != s.find(vertex2)) {

				s.union(s.find(vertex1), s.find(vertex2));
				dist += current.dist;

			}

		}
			
		return dist;

	}
}
