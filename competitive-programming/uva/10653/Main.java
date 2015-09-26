import java.util.*;

class Main {

	static int[][] board = new int[1050][1050];
	static boolean[][] visited = new boolean[1050][1050];

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		while (true) {
		
			int rows = input.nextInt();
			int cols = input.nextInt();

			if (rows == 0 & cols == 0) break;

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					visited[i][j] = false;
					board[i][j] = 0;
				}
			}

			int rowsWithBombs = input.nextInt();

			for (int i = 0; i < rowsWithBombs; i++) {
				int row = input.nextInt();
				int bombsInRow = input.nextInt();
				for (int j = 0; j < bombsInRow; j++) {
					board[row][input.nextInt()] = 1;
				}
			}

			int rowStart = input.nextInt();
			int colStart = input.nextInt();

			int rowDest = input.nextInt();
			int colDest = input.nextInt();

			System.out.println(bfs(rows, cols, rowStart, colStart, rowDest, colDest));

		}
		
	}

	static int bfs(int rows, int cols, int rowStart, int colStart, int rowDest, int colDest) {

		LinkedList<int[]> q = new LinkedList<int[]>();

		q.add(new int[]{rowStart, colStart, 0});

		int height = rows;
		int width = cols;

		while(!q.isEmpty()) {

			int[] cur = q.poll();
			int curR = cur[0];
			int curC = cur[1];
			int curD = cur[2];

			visited[curR][curC] = true;

			if (curR == rowDest && curC == colDest) return curD;

			if (curR - 1 > -1 && board[curR - 1][curC] != 1 && !visited[curR - 1][curC]) {
				visited[curR - 1][curC] = true;
				q.add(new int[]{curR - 1, curC, curD + 1});
			}

			if (curR + 1 < height && board[curR + 1][curC] != 1 && !visited[curR + 1][curC]) {
				visited[curR + 1][curC] = true;
				q.add(new int[]{curR + 1, curC, curD + 1});
			}

			if (curC - 1 > -1 && board[curR][curC - 1] != 1 && !visited[curR][curC - 1]) {
				visited[curR][curC - 1] = true;
				q.add(new int[]{curR, curC - 1, curD + 1});
			}

			if (curC + 1 < width && board[curR][curC + 1] != 1 && !visited[curR][curC + 1]) {
				visited[curR][curC + 1] = true;
				q.add(new int[]{curR, curC + 1, curD + 1});
			}

		}

		return -1;

	}

}

