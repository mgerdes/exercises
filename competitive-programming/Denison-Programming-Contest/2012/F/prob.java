import java.util.*;

class Main {

	static int[] profits;
	static int[] distances;
	static int minDistance;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int numberOfStores;
		int caseNum = 1;

		while ((numberOfStores = input.nextInt()) != 0) {
			minDistance = input.nextInt();

			profits = new int[numberOfStores];
			distances = new int[numberOfStores - 1];

			for (int i = 0; i < numberOfStores; i++) {
				profits[i] = input.nextInt();
				if (i != numberOfStores - 1) {
					distances[i] = input.nextInt();
				}
			}

			System.out.printf("Case %d: %d\n", caseNum++, maxProfit(0, numberOfStores));
		}
	}

	public static int maxProfit(int i, int j) {
		int maxProfit = 0;

		for (int k = i; k < j; k++) {
			int currentProfit = 0;
			if (i == 0 || distance(i - 1, k) >= minDistance) {
				if (k == j - 1) {
					currentProfit += profits[k];	
				} else {
					currentProfit = profits[k] + maxProfit(k + 1, j);
				}
			}
			maxProfit = Math.max(maxProfit, currentProfit);
		}

		return maxProfit;
	}

	public static int distance(int i, int j) {
		int distance = 0;
		for (int k = i; k < j; k++) {
			distance += distances[k];	
		}
		return distance;
	}

}
