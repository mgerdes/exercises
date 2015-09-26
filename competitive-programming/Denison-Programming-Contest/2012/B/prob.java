import java.util.*;

class Main {
	static int n;
	static int[][] zeros = new int[20 * 20][2];
	static int numberOfZeros;

	static boolean[] usedRow = new boolean[20];
	static boolean[] usedCol = new boolean[20];

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int caseNum = 1;

		while ((n = input.nextInt()) != 0) {
			numberOfZeros = 0;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int number = input.nextInt();
					if (number == 0) {
						zeros[numberOfZeros++] = new int[]{i, j};
					}
				}
			}

			System.out.printf("Case %d: %s\n", caseNum++, isValid() ? "YES" : "NO");
		}
	}

	public static void reset() {
		for (int i = 0; i < n; i++) {
			usedRow[i] = usedCol[i] = false;
		}
	}

	public static boolean isValid() {
		for (int i = 0; i < numberOfZeros; i++) {
			reset();
			if (addZero(i, 1)) return true;
		}
		return false;
	}

	public static boolean addZero(int zeroIndex, int zerosFound) {
		int row = zeros[zeroIndex][0];
		int col = zeros[zeroIndex][1];
		usedRow[row] = true;
		usedCol[col] = true;

		if (zerosFound == n) return true;

		for (int i = zeroIndex + 1; i < numberOfZeros; i++) {
			if (noZerosLike(i)) {
				if (addZero(i, zerosFound + 1)) return true;
				removeZero(i);
			} 
		}
		
		return false;
	}

	public static void removeZero(int zeroIndex) {
		int row = zeros[zeroIndex][0];
		int col = zeros[zeroIndex][1];
		usedRow[row] = false;
		usedCol[col] = false;
	}

	public static boolean noZerosLike(int zeroIndex) {
		int row = zeros[zeroIndex][0];
		int col = zeros[zeroIndex][1];

		if (usedRow[row]) return false; 
		if (usedCol[col]) return false; 

		return true;
	}

}
