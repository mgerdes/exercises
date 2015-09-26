import java.util.*;

class Main {

	public static void main (String[] args) {

		Scanner input = new Scanner(System.in);
		ArrayList<Integer> height = new ArrayList<Integer>();
		int[] length;
		int[] seq;

		int n = Integer.parseInt(input.nextLine());
		input.nextLine();

		for (int i = 0; i < n; i++) {

			height.clear();

			int nm = 0; // number of missiles

			String next;
			while (input.hasNext() && !(next = input.nextLine()).equals("")) {
				height.add(Integer.parseInt(next));
				nm++;
			}

			seq = new int[nm];
			length = new int[nm];
			Arrays.fill(length, 1);

			for (int cur = 1; cur < nm; cur++) {
				for (int prev = 0; prev < cur; prev++) {

					if (height.get(cur) > height.get(prev) && length[cur] < length[prev] + 1) {
						length[cur] = length[prev] + 1;
						seq[cur] = prev;
					}

				}
			}

			int missile = 0;
			int max = 0;
			for (int j = 0; j < nm; j++) {
				if (length[j] > max) {
					missile = j;
					max = length[j];
				}
			}

			int[] order = new int[max];
			for (int j = 0; j < max; j++) {
				order[j] = missile;
				missile = seq[missile];
			}

			System.out.println("Max hits: " + max);

			for (int j = max - 1; j >= 0; j--) {
				System.out.println(height.get(order[j]));
			}

			if (input.hasNext()) System.out.println();

		}

	}

}
