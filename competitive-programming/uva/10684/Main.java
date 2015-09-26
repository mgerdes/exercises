import java.util.*;

class Main {
	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);

		int n;	
		while ((n = input.nextInt()) != 0) {

			int currentSum = 0;
			int maxSum = 0;

			for (int i = 0; i < n; i++) {
				int next = input.nextInt();

				currentSum += next;
				
				if (currentSum > maxSum) maxSum = currentSum;
				if (currentSum < 0) currentSum = 0;
			}

			System.out.println((maxSum > 0) ? "The maximum winning streak is " + maxSum + "." : "Losing streak.");
		}
	}
}
