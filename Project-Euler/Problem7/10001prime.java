import java.util.*;

class Main {
	public static final int MAX = 10001;

	public static void main(String[] args) {
		int numOfPrimes = 1, num = 3;
		while(true) {
			if (isPrime(num)) {
				numOfPrimes++;
			}	
			if (numOfPrimes == MAX) break;
			num++;
		}
		System.out.println(num);
	}

	public static boolean isPrime(long num) {
		for (long i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}	
}
