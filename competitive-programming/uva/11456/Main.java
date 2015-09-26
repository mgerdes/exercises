import java.util.*;

class Main {

	static int[] weight = new int[2005];
	static int[] lisA = new int[2005];
	static int[] ldsA = new int[2005];
	static int nc;

	public static void main (String[] args) {

		Scanner input = new Scanner(System.in);

		int n = input.nextInt(); // num of test cases;
		for (int i = 1; i <= n; i++) {

			nc = input.nextInt(); // num of cars;
			for (int j = 0; j < nc; j++) {
				weight[j] = input.nextInt();
				lisA[j] = 0;
				ldsA[j] = 0;
			}

			int max = 0;

			for (int j = 0; j < nc; j++) {

				int lis = lis(j);
				int lds = lds(j);

				if (lis + lds - 1 > max) max = lis + lds - 1;

			}

			System.out.println(max);

		}

	}

	static int lis(int i) {

		int lis = 1;
		int max = 0;

		if (lisA[i] == 0) {

			for (int j = i + 1; j < nc; j++) {
				if (weight[j] > weight[i]) {

					int cur = lis(j);
					if (cur > max) max = cur;

				}
			}

			lisA[i] = lis + max;

			return lis + max;

		} else {

			return lisA[i];

		}

	}

	static int lds(int i) {

		int lds = 1;
		int max = 0;

		if (ldsA[i] == 0) {

			for (int j = i + 1; j < nc; j++) {
				if (weight[j] < weight[i]) {

					int cur = lds(j);
					if (cur > max) max = cur;

				}
			}

			ldsA[i] = lds + max;

			return lds + max;

		} else {

			return ldsA[i];

		}

	}


}