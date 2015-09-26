#include <stdio.h>

#define MAX 400000000

int q[MAX + 1];
int qSize = 0;
int qIndex = 0;

char grid[1050][1050];
int visited[1050][1050];
int fireInit[MAX + 1][2];

main() {
	int N;
	scanf("%d", &N);

	int i;
	for (i = 0; i < N; i++) {
		int R;
		int C;
		scanf("%d %d", &R, &C);

		int joeInitRow;
		int joeInitCol;
		int n = 0;

		int j;
		for (j = 0; j < R; j++) {
			char arr[C];
			scanf("%s", arr);

			int k;
			for (k = 0; k < sizeof(arr); k++) {
				grid[j][k] = arr[k];

				if (arr[k] == 'J') {
					joeInitRow = j;
					joeInitCol = k;
				}

				if (arr[k] == 'F') {
					fireInit[n][0] = j;
					fireInit[n][1] = k;
					n++;
				}
			}
		}

		int minutes = bfs(R,C,n,joeInitRow,joeInitCol);

		if (minutes == -1) {
			printf("%s\n", "IMPOSSIBLE");
		} else {
			printf("%d\n", minutes);
		}

	}


	return 0;
}

int bfs(int rows, int cols, int n, int joeInitRow, int joeInitCol) {
	clearQueue();

	int i;
	int j;
	for (i = 0; i < rows; i++) {
		for (j = 0; j < cols; j++) {
			visited[i][j] = 0;
		}
	}

	for (i = 0; i < n; i++) {
		add(0);
		add(0);
		add(fireInit[i][0]);
		add(fireInit[i][1]);
	}

	add(1);
	add(1);
	add(joeInitRow);
	add(joeInitCol);

	visited[joeInitRow][joeInitCol] = 1;

	while (qSize != 0) {
		int type = poll();
		int curMinute = poll();
		int curRow = poll();
		int curCol = poll();

		if (type == 1) {

			if (curRow == rows - 1 || curCol == cols - 1 || curCol == 0 || curRow == 0) return curMinute;

			if (curRow + 1 < rows) {
				if (visited[curRow + 1][curCol] == 0 && grid[curRow + 1][curCol] != '#' && grid[curRow + 1][curCol] != 'F') {
					visited[curRow + 1][curCol] = 1;

					add(1);
					add(curMinute + 1);
					add(curRow + 1);
					add(curCol);
				}
			}

			if (curRow - 1 > -1) {
				if (visited[curRow - 1][curCol] == 0 && grid[curRow - 1][curCol] != '#' && grid[curRow - 1][curCol] != 'F') {
					visited[curRow - 1][curCol] = 1;

					add(1);
					add(curMinute + 1);
					add(curRow - 1);
					add(curCol);
				}
			}

			if (curCol + 1 < cols) {
				if (visited[curRow][curCol + 1] == 0 && grid[curRow][curCol + 1] != '#' && grid[curRow][curCol + 1] != 'F') {
					visited[curRow][curCol + 1] = 1;

					add(1);
					add(curMinute + 1);
					add(curRow);
					add(curCol + 1);
				}
			}

			if (curCol - 1 > -1) {
				if (visited[curRow][curCol - 1] == 0 && grid[curRow][curCol - 1] != '#' && grid[curRow][curCol - 1] != 'F') {
					visited[curRow][curCol - 1] = 1;

					add(1);
					add(curMinute + 1);
					add(curRow);
					add(curCol - 1);
				}
			}


		} else {

			if (curRow + 1 < rows) {
				if (grid[curRow + 1][curCol] != '#' && grid[curRow + 1][curCol] != 'F') {
					grid[curRow + 1][curCol] = 'F';

					add(0);
					add(0);
					add(curRow + 1);
					add(curCol);
				}
			}

			if (curRow - 1 > -1) {
				if (grid[curRow - 1][curCol] != '#' && grid[curRow - 1][curCol] != 'F') {
					grid[curRow - 1][curCol] = 'F';

					add(0);
					add(0);
					add(curRow - 1);
					add(curCol);
				}
			}

			if (curCol + 1 < cols) {
				if (grid[curRow][curCol + 1] != '#' && grid[curRow][curCol + 1] != 'F') {
					grid[curRow][curCol + 1] = 'F';

					add(0);
					add(0);
					add(curRow);
					add(curCol + 1);
				}
			}

			if (curCol - 1 > -1) {
				if (grid[curRow][curCol - 1] != '#' && grid[curRow][curCol - 1] != 'F') {
					grid[curRow][curCol - 1] = 'F';
						
					add(0);
					add(0);
					add(curRow);
					add(curCol - 1);
				}
			}
		}

	}

	return -1;
}

int poll() {
	if (qIndex < MAX) {
		qSize--;
		qIndex++;
		return q[qIndex - 1];
	}
	return -1;
}

int add(int x) {
	if (qIndex + qSize < MAX) {
		q[qIndex + qSize] = x;
		qSize++;
		return 1;
	} else {
		return -1;
	}
}

int clearQueue() {
	qIndex = 0;
	qSize = 0;
	return 1;
}