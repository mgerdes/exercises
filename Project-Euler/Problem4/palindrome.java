import java.util.*;

class Main {

	public static void main(String[] args) {

		int largest = 0;

		for (int i = 100; i < 1000; i++) {
			for (int j = 100; j < 1000; j++) {
				if(isPalindrome(i * j)) {
					if (i * j > largest) largest = i * j;
				}
			}
		}

		System.out.println(largest);

	}

	public static boolean isPalindrome(int num) {

		int one,two,three,four,five,six;

		if (num / 100000 > 1) {
			// num has 6 digits
			one = num / 100000;
			two = num / 10000 % 10;
			three = num / 1000 % 10;
			four = num / 100 % 10;
			five = num / 10 % 10;
			six = num % 10;

			return (one == six && two == five && three == four);

		} else {
			// num has 5 digits
			one = num / 10000;
			two = num / 1000 % 10;
			three = num / 100 % 10;
			four = num / 10 % 10;
			five = num % 10;

			return (one == five && two == four);
		}

	}
}
