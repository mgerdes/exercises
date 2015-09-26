import java.util.*;

class Main {

	public static final int MAX = 1000;

	public static void main(String[] args) {
		int a, b, c;

		loops:
		for (a = 1; a <= MAX; a++) {
			for (b = 1; b <= MAX; b++) {
				for (c = 1; c <= MAX; c++) {
					if (a * a + b * b == c * c && a < b && b < c && a + b + c == 1000) {
						System.out.println(a * b * c);	
						break loops;
					}
				}
			}
		}

	}

}
