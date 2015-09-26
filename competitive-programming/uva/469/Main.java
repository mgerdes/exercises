import java.util.*;

class Main {
	static char[][] land = new char[100][0];
	static boolean[][] visited = new boolean[100][100];
	static int height = 100;
	static int area;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int n = input.nextInt();

		for (int i = 0; i < n; i++) {
			String rowString = input.next();
			land[0] = rowString.toCharArray();

			int r = 1; // row number
			while (!input.hasNextInt()) {
				rowString = input.next();
				land[r] = rowString.toCharArray();
				r++;
			}

			height = r;

			while (input.hasNextInt()) {
				clear();
				int row = input.nextInt() - 1;
				int col = input.nextInt() - 1;
				getArea(row, col);
				System.out.println(area);
			}
			if (i < n - 1)
				System.out.println();
		}
	}

	static void getArea(int row, int col) {
		if (row > -1 && col > -1 && row < height && col < land[row].length) {
			if (land[row][col] == 'W' && !visited[row][col]) {
				visited[row][col] = true;
				area++;
				getArea(row - 1, col - 1);
				getArea(row, col - 1);
				getArea(row + 1, col - 1);
				getArea(row + 1, col);
				getArea(row + 1, col + 1);
				getArea(row, col + 1);
				getArea(row - 1, col + 1);
				getArea(row - 1, col);
			}
		}
	}

	static void clear() {
		area = 0;
		for (int i = 0; i < height; i++)
			for (int j = 0; j < land[i].length; j++) {
				visited[i][j] = false;
			}
	}
}
