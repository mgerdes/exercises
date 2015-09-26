import java.util.*;

class Main {

	static int[] bobsHeight;
	static int[] alicesHeight;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int historyLength;
		int caseNum = 1;

		while ((historyLength = input.nextInt()) != 0) {
			String history = input.next();
			
			int bobsTime = 0;
			int alicesTime = 0;
			int height = 0;

			bobsHeight = new int[historyLength];
			alicesHeight = new int[historyLength];

			for (int i = 0; i < historyLength * 2; i++) {
				if (history.charAt(i) == 'A') {
					alicesHeight[alicesTime++] = height++;
				} else {
					bobsHeight[bobsTime++] = height++;
				}
			}

			String result = getResult();

			System.out.printf("Case %d: %s\n", caseNum++, result);

		}

	}

	public static String getResult() {
		char startTallest = bobsHeight[0] > alicesHeight[0] ? 'B' : 'A';

		for (int i = 1; i < bobsHeight.length; i++) {
			if (startTallest == 'B') {
				if (alicesHeight[i] > bobsHeight[i]) return "Mixed";
			} else {
				if (alicesHeight[i] < bobsHeight[i]) return "Mixed";
			}
		}

		if (startTallest == 'B') {
			return "Bob";
		} else {
			return "Alice";
		}
	}

}
