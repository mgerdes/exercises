import java.util.*;

class Main {

	static int[] batteries = new int[10005];
	static int numberOfTrains;
	static int numberOfBatsPerTrain;
	static int numberOfBats;
	static int minDiff;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int caseNum = 1;

		while ((numberOfTrains = input.nextInt()) != 0) {
			numberOfBatsPerTrain = input.nextInt();

			numberOfBats = input.nextInt();

			for (int i = 0; i < numberOfBats; i++) {
				batteries[i] = input.nextInt();
			}

			Arrays.sort(batteries, 0, numberOfBats);

			solveProblem();

			System.out.printf("Case %d: %d\n", caseNum++, minDiff);
		}
	}

	public static void solveProblem() {
		for (int diff = 0; diff < 101; diff++) {
			for (int i = 0; i < numberOfBats; i++) {

				int batteriesWithinDiff = 1;
				for (int j = i + 1; j < numberOfBats; j++) {

					if (batteries[j] - batteries[i] > diff) {
						break;
					}

					batteriesWithinDiff++;

					if ((batteriesWithinDiff == numberOfTrains) && (numberOfBats - j > (numberOfBatsPerTrain - 1) * numberOfTrains)) {
						minDiff = diff;
						return;
					}
				}

			}
		}
	}

}
