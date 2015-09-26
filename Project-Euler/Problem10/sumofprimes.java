import java.util.*;

class Main {

	static final int MAX = 2000000;

	public static void main(String[] args) {

		int numbers[] = new int[MAX];
		boolean covered[] = new boolean[MAX];

		for (int i = 0; i < MAX; i++) {
			numbers[i] = i;
			covered[i] = false;
		}

		long sum = 0;
		for (int i = 2; i < MAX; i++) {
			if (!covered[i]) {
				sum += i;
			}

			for (int j = i; j < MAX; j += i) {
				covered[j] = true;
			}
		}

		System.out.println(sum);

	}

}
