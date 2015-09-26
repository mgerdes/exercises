/*
10
3 1 2 4 9 5 10 6 8 7
1 2 3 4 5 6 7 8 9 10
4 7 2 3 10 6 9 1 5 8
3 1 2 4 9 5 10 6 8 7
2 10 1 3 8 4 9 5 7 6
*/

import java.util.*;

class Main {

	static int[] event = new int[25];
	static int[] seq = new int[25];
	static int[] length = new int[25];

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int n = input.nextInt(); // number of events

		for (int i = 1; i <= n; i++) {
			event[i] = input.nextInt();
		}

		while (input.hasNextInt()) {
			
			Arrays.fill(length, 1);

			for (int i = 1; i <= n; i++) {
				seq[event[i]] = input.nextInt();
			}

			for (int cur = 2; cur <= n; cur++) {
				for (int prev = 1; prev < cur; prev++) {

					if (seq[prev] < seq[cur]) {
						if (length[cur] < length[prev] + 1) length[cur] = length[prev] + 1;										
					}

				}
			}

			int max = 0;
			for (int i = 1; i <= n; i++) {
				max = Math.max(length[i], max);
			}

			System.out.println(max);

		}

	}

}