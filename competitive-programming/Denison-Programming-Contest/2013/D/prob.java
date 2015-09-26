import java.util.*;

class Main {
	static boolean[] isPrime = new boolean[1000002];

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int n;	
		int caseNum = 1;
		
		getPrimes();

		while((n = input.nextInt()) != 0) {
			System.out.printf("Case %d: %d\n", caseNum++, spread(n));	
		}
	}

	public static int spread(int n) {
		int largest = 0;
		int smallest = Integer.MAX_VALUE;

		for (int i = 2; i < n; i++) {
			if (isPrime[i] && isPrime[n - i]) {
				if (Math.abs(n - i - i) > largest) largest = Math.abs(n - i - i);
				if (Math.abs(n - i - i) < smallest) smallest = Math.abs(n - i - i);
			}
		}

		return largest - smallest;
	}

	public static void getPrimes() {
		for (int i = 2; i < isPrime.length; i++) {
			isPrime[i] = true;
		}

		for (int i = 2; i < isPrime.length; i++) {
			if (isPrime[i]) coverUp(i);	
		}
	}

	public static void coverUp(int i) {
		for (int j = i + i; j < isPrime.length; j += i) {
			isPrime[j] = false;
		}
	}
}
