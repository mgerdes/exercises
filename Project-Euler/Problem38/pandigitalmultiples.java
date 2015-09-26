import java.util.*;

class Main {

	static final int MAX_DIGITS = 9;

	public static void main(String[] args) {

		String s = "";
		int num = 1;
		int multiplier = 1;
		int n = 2;

		while (n > 1) {
			s = "";
			n = 1;
			while (s.length() <= 9) {
				s += num * multiplier;
				multiplier++;	
				n++;
			}
			num++;
		}

	}

}
