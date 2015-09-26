import java.util.*;

class Main {
	static ArrayList<ArrayList<Integer>> connected = new ArrayList<ArrayList<Integer>>(10000);
	static boolean[] knockedOver = new boolean[10000];
	static int ko; // amount knocked over.
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10000; i++) {
			connected.add(new ArrayList<Integer>());
		}
		
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		
		for (int i = 0; i < n; i++) {
			int d = input.nextInt(); // dominos
			int c = input.nextInt(); // connections
			int h = input.nextInt(); // hand
						
			for (int j = 0; j < d; j++) {
				connected.get(j).clear();
				knockedOver[j] = false;
			}
			
			ko = 0;
			
			for (int j = 0; j < c; j++) {
				int one = input.nextInt() - 1;
				int two = input.nextInt() - 1;
				connected.get(one).add(two);
			}
			
			for (int j = 0; j < h; j++) {
				knockOver(input.nextInt() - 1);
			}
			
			System.out.println(ko);
		}
	}
	
	static void knockOver(int source) {
		// knock the source over
		if (!knockedOver[source]) {
			//System.out.println("Knocked over " + source);
			knockedOver[source] = true;
			ko++;
			// go to each domino connected to source.
			for (int i = 0; i < connected.get(source).size(); i++) {
				int dest = connected.get(source).get(i);
				if (!knockedOver[dest])
					knockOver(dest);
			}
		}
	}
}
