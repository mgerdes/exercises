import java.util.*;

class Main {
	static char[][] grid = new char[1050][1050];
	static boolean[][] visited = new boolean[1050][1050];
	static char[] arr;

	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);

		int N = input.nextInt();

		for (int i = 0; i < N; i++) {
			int R = input.nextInt();
			int C = input.nextInt();

			int joeInitRow = 0;
			int joeInitCol = 0;

			ArrayList<Integer> fireInits = new ArrayList<Integer>();

			for (int j = 0; j < R; j++) {
				arr = input.next().toCharArray();

				for (int k = 0; k < C; k++) {
					grid[j][k] = arr[k];

					if (arr[k] == 'J') {
						joeInitRow = j;
						joeInitCol = k;
					}
					if (arr[k] == 'F') {
						fireInits.add(j);
						fireInits.add(k);
					}
				}

			}

			int minutes = bfs(R,C,joeInitRow,joeInitCol,fireInits);

			System.out.println((minutes == -1) ? "IMPOSSIBLE" : minutes);
		}

	}
	
	static int bfs(int rows, int cols, int joeInitRow, int joeInitCol, ArrayList<Integer> fireInits) {
		clearVisited(rows, cols);

		LinkedList<Integer> q = new LinkedList<Integer>();

		for (int i = 0; i < fireInits.size(); i+=2) {
			q.add(0);
			q.add(0);
			q.add(fireInits.get(i));
			q.add(fireInits.get(i + 1));
		}

		q.add(1);
		q.add(1);
		q.add(joeInitRow);
		q.add(joeInitCol);

		visited[joeInitRow][joeInitCol] = true;
		
		while (!q.isEmpty()) {
			int type = q.poll();
			int curMinute = q.poll();
			int curRow = q.poll();
			int curCol = q.poll();

			if (type == 1) {

				if (curRow == rows - 1 || curCol == cols - 1 || curCol == 0 || curRow == 0) return curMinute;

				if (curRow + 1 < rows) {
					if (!visited[curRow + 1][curCol] && grid[curRow + 1][curCol] != '#' && grid[curRow + 1][curCol] != 'F') {
						visited[curRow + 1][curCol] = true;

						q.add(1);
						q.add(curMinute + 1);
						q.add(curRow + 1);
						q.add(curCol);
					}
				}

				if (curRow - 1 > -1) {
					if (!visited[curRow - 1][curCol] && grid[curRow - 1][curCol] != '#' && grid[curRow - 1][curCol] != 'F') {
						visited[curRow - 1][curCol] = true;

						q.add(1);
						q.add(curMinute + 1);
						q.add(curRow - 1);
						q.add(curCol);
					}
				}

				if (curCol + 1 < cols) {
					if (!visited[curRow][curCol + 1] && grid[curRow][curCol + 1] != '#' && grid[curRow][curCol + 1] != 'F') {
						visited[curRow][curCol + 1] = true;

						q.add(1);
						q.add(curMinute + 1);
						q.add(curRow);
						q.add(curCol + 1);
					}
				}

				if (curCol - 1 > -1) {
					if (!visited[curRow][curCol - 1] && grid[curRow][curCol - 1] != '#' && grid[curRow][curCol - 1] != 'F') {
						visited[curRow][curCol - 1] = true;

						q.add(1);
						q.add(curMinute + 1);
						q.add(curRow);
						q.add(curCol - 1);
					}
				}


			} else {

				if (curRow + 1 < rows) {
					if (grid[curRow + 1][curCol] != '#' && grid[curRow + 1][curCol] != 'F') {
						grid[curRow + 1][curCol] = 'F';

						q.add(0);
						q.add(0);
						q.add(curRow + 1);
						q.add(curCol);
					}
				}

				if (curRow - 1 > -1) {
					if (grid[curRow - 1][curCol] != '#' && grid[curRow - 1][curCol] != 'F') {
						grid[curRow - 1][curCol] = 'F';

						q.add(0);
						q.add(0);
						q.add(curRow - 1);
						q.add(curCol);
					}
				}

				if (curCol + 1 < cols) {
					if (grid[curRow][curCol + 1] != '#' && grid[curRow][curCol + 1] != 'F') {
						grid[curRow][curCol + 1] = 'F';

						q.add(0);
						q.add(0);
						q.add(curRow);
						q.add(curCol + 1);
					}
				}

				if (curCol - 1 > -1) {
					if (grid[curRow][curCol - 1] != '#' && grid[curRow][curCol - 1] != 'F') {
						grid[curRow][curCol - 1] = 'F';
						
						q.add(0);
						q.add(0);
						q.add(curRow);
						q.add(curCol - 1);
					}
				}

			}
		}

		return -1;

	}

	static void clearVisited(int rows, int cols) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				visited[i][j] = false;
			}
		}
	}
}
