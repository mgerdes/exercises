/*
5 2
3 7
8 10
5 2
9 11
21 18
8 6
5 2 20 1 30 10
23 15 7 9 11 3
40 50 34 24 14 4
9 10 11 12 13 14
31 4 18 8 27 17
44 32 13 19 41 19
1 2 3 4 5 6
80 37 47 18 21 9
*/

import java.util.*;

class Main {

	static int[][] box;
	static int[] inside;
	static int[] prev;

	static int n;
	static int d;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		while(input.hasNext()) {

			n = input.nextInt(); // number of boxes.
			d = input.nextInt(); // dimension.

			box = new int[n][d];
			inside = new int[n];
			prev = new int[n];

			for (int i = 0; i < n; i++) {				
				for (int j = 0; j < d; j++) {
					box[i][j] = input.nextInt();
				}
			}

			sort();

			int maxInside = 0;
			int index = 0;
			for (int i = 0; i < n; i++) {

				int boxesInside = boxesInside(i);
				if (boxesInside > maxInside) {
					maxInside = boxesInside;
					index = i;
				}

			}

			maxInside++;

			ArrayList<Integer> seq = new ArrayList<Integer>();

			for (int i = 0; i < maxInside; i++) {
				seq.add(index);
				index = prev[index];
			}

			System.out.println(maxInside);

			for (int i = seq.size() - 1; i >= 0; i--) {
				System.out.print(seq.get(i) + 1);
				if (i != 0) System.out.print(" ");
			}

			System.out.println();

		}

	}

	static void sort() {
		for (int i = 0; i < n; i++) {
			Arrays.sort(box[i]);
		}
	}

	static int boxesInside(int i) {

		if (inside[i] == 0) {

			int max = 0;

			for (int j = 0; j < n; j++) {

				boolean inside = true;

				for (int k = 0; k < d; k++) {
					if (box[j][k] >= box[i][k]) inside = false;
				}

				if (inside) {
					int temp = boxesInside(j);
					if (temp + 1 > max) {
						max = temp + 1;
						prev[i] = j;
					}
				}

			}

			inside[i] = max;
			return max;

		} else {

			return inside[i];

		}

	}

}