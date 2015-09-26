import java.util.*;

class Main {

	final static long NUMBER = 600851475143L;

	public static void main(String[] args) {
		long largest = 1;

		for (long i = 1; i < Math.sqrt(NUMBER); i++) {
			if (NUMBER % i == 0 && isPrime(i)) {
				largest = i;
			}
		}

		System.out.println(largest);
	}

	public static boolean isPrime(long num) {
		for (long i = 2; i < Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

}
