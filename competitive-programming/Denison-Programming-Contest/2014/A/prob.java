import java.util.*;

class Main {
	static int[] cakeWeights;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int caseNum = 1;
		int numberOfCakes;

		while ((numberOfCakes = input.nextInt()) != 0) {

			cakeWeights = new int[numberOfCakes];

			for (int i = 0; i < numberOfCakes; i++) {
				cakeWeights[i] = input.nextInt();
			}
			
			System.out.printf("Case %d: %s\n", caseNum++, possibleToSplit() ? "YES" : "NO");
		}
		
	}

	public static boolean possibleToSplit() {
		int totalWeight = totalWeight();	

		if (cakeWeights.length % 2 == 1) return false;
		if (totalWeight % 2 == 1) return false;

		int splitWeight = totalWeight / 2;
		int splitNumber = cakeWeights.length / 2;

		// can splitNumber weights add up to splitWeight?
		return isSolution(splitNumber, splitWeight, 0, cakeWeights.length - 1);
	}

	public static boolean isSolution(int totalNumbers, int totalSum, int i, int j) {
		for (int k = i + 1; k <= j; k++) {
			if (totalNumbers == 1) {
				if (cakeWeights[k] == totalSum) return true;
			} else if (isSolution(totalNumbers - 1, totalSum - cakeWeights[i], k, j)) {
				return true;
			}
		}
		return false;
	}

	public static int totalWeight() {
		int totalWeight = 0;
		for (int cakeWeight : cakeWeights) {
			totalWeight += cakeWeight;	
		}
		return totalWeight;
	}
}
