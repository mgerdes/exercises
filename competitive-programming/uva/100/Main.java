import java.util.*;

class Main {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int[] N = new int[1000000];

		while (input.hasNextInt()) {
			
			// This is the 3n + 1 problem

			int i = input.nextInt();
			int j = input.nextInt();
			int max = 0;

			int start = i;
			int end = j;

			if (j < i) {
				start = j;
				end = i;
			}

			for (int k = start; k <= end; k++) {
				long x = k;
				int n = 1;

				while (x != 1) {
					if (x % 2 != 0) {
						x = x * 3 + 1;
					} else {
						x = x / 2;
					}
					if(x < 1000000 && N[(int)x] != 0) {
						n = n + N[(int)x];
						break;
					}
					n++;
				}

				N[k] = n;

				if (n > max) {
					max = n;
				}
			}

			System.out.println(i + " " + j + " " + max);

		}
	}
}
