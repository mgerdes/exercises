import java.util.*;

class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		

		int n, m;
		int caseNum = 1;

		while ((n = input.nextInt()) != 0) {
			m = input.nextInt();
			
			System.out.printf("Case %d: %d\n", caseNum++, N(n,m));
		}
	}

	public static int N(int n, int m) {
		if (m == 1 || n == m) return 1;
		return m * N(n - 1, m) + N(n - 1, m - 1);
	}
}
