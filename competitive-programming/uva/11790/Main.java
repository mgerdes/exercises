import java.util.*;

class Main {

	static int[] width;
	static int[] height;

	static int[] lds;
	static int[] lis;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int n = input.nextInt(); // number of cases;

		for (int i = 0; i < n; i++) {
			int nb = input.nextInt(); // number of buildings;

			height = new int[nb];
			width = new int[nb];

			lis = new int[nb];
			lds = new int[nb];

			for (int j = 0; j < nb; j++) {
				height[j] = input.nextInt();
			}

			for (int j = 0; j < nb; j++) {
				width[j] = input.nextInt();
			}

			for (int j = 0; j < nb; j++) {
				lds[j] = lis[j] = width[j];
			}

			for (int cur = 1; cur < nb; cur++) {
				for (int prev = 0; prev < cur; prev++) {

					if (height[prev] < height[cur]) { 
						// increasing
						if (lis[prev] + width[cur]  > lis[cur]) {
							lis[cur] = lis[prev] + width[cur];
						}
					}

					if (height[prev] > height[cur]) {
						// decreasing
						if (lds[prev] + width[cur]  > lds[cur]) {
							lds[cur] = lds[prev] + width[cur];
						}
					}

				}
			}

			int maxlds = 0;
			int maxlis = 0;

			for (int j = 0; j < nb; j++) {
				if (lds[j] > maxlds) maxlds = lds[j];
				if (lis[j] > maxlis) maxlis = lis[j];
			}

			System.out.print("Case " + (i + 1) + ". ");

			if (maxlis >= maxlds) {
				System.out.print("Increasing (" + maxlis + "). Decreasing (" + maxlds + ").");
			} else {
				System.out.print("Decreasing (" + maxlds + "). Increasing (" + maxlis + ").");
			}

			System.out.println();

		}

	}

}