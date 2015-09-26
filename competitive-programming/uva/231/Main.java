import java.util.*;

class Main {

	public static void main (String[] args) {

		Scanner input = new Scanner(System.in);

		int testNum = 1;
		int first;

		while ((first = input.nextInt()) != -1) {

			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(first);

			int next;

			while ((next = input.nextInt()) != -1) {
				list.add(next);
			}

			int[] length = new int[list.size()];

			Arrays.fill(length, 1);

			for (int i = list.size() - 2; i >= 0; i--) {
				for (int j = list.size() - 1; j > i; j--) {
				
					if (list.get(i) > list.get(j) && length[j] > length[i}) {
						length[i] = length[j] + 1;
					}

				}
			} 

			int maxLength = 0;

			for (int i = list.size() - 1; i >= 0; i--) {
				if (length[i] > maxLength) maxLength = length[i];
			}

			if (testNum != 1) {
				System.out.println();
			}

			System.out.println("Test #" + testNum + ":");
			System.out.println("  maximum possible interceptions: " + maxLength);

			testNum++;

		}

	}

}
