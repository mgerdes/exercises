import java.util.*;

class Main {
	static boolean[] found = new boolean[524290];

	static int length;
	static int upperBound;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int caseNum = 1;

		while ((length = input.nextInt()) != 0) {
			upperBound = (int) Math.pow(2, length);
			reset();
			System.out.printf("Case %d: %d\n", caseNum++, numberOfLyndonWords());	
		}
	}

	public static void reset() {
		for (int i = 0; i < upperBound; i++) {
			found[i] = false;	
		}
	}

	public static int numberOfLyndonWords() {
		int number = 0;
		for (int i = 0; i < upperBound; i++) {
			if (!found[i]) { 
				if (isLyndon(intToBinaryString(i))) number++;
			}
		}
		return number;
	}

	public static boolean isLyndon(String word) {
		ArrayList<Integer> numbersFoundInCycle = new ArrayList<Integer>();

		int number = binaryStringToInt(word);
		numbersFoundInCycle.add(number);
		found[number] = true;

		for (int i = 0; i < word.length() - 1; i++) {
			char first = word.charAt(0);
			word = word.substring(1, word.length());
			word = word + first;

			number = binaryStringToInt(word);
			if (numbersFoundInCycle.contains(new Integer(number))) return false;

			numbersFoundInCycle.add(number);
			found[number] = true;
		}
		return true;
	}

	public static int binaryStringToInt(String binaryString) {
		int value = 0;
		int stringLength = binaryString.length();
		for (int i = 0; i < stringLength; i++) {
			if (binaryString.charAt(i) == '1') value += (int) Math.pow(2, stringLength - i - 1);
		}
		return value;
	}

	public static String intToBinaryString(int number) {
		String binaryString = "";
		while (number != 0) {
			binaryString = (number % 2) + "" + binaryString;
			number = number / 2;
		}
		while (binaryString.length() < length) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}
}
