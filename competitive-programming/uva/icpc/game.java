import java.util.*;

public class game {

	static int[][][] position;
	static int[][] movesA;
	static int[][] maxA;
	static int[][] minA;
	static int huge = Integer.MAX_VALUE;

	public static void main (String[] args) {

		Scanner input = new Scanner(System.in);

		int np = input.nextInt(); // number of positions.
		position = new int[np][0][0];

		for (int pos = 0; pos < np; pos++) {

			int ns = input.nextInt(); // number of sequences.
			position[pos] = new int[ns][0];

			for (int seq = 0; seq < ns; seq++) {
				char[] moves = input.next().toCharArray();
				position[pos][seq] = new int[moves.length];

				for (int i = 0; i < moves.length; i++) {
					position[pos][seq][i] = moves[i] - 'a';
					//System.out.println(position[pos][seq][i]);
				}
			}

		}

		minA = new int[np][np];
		maxA = new int[np][np];

		for (int i = 0; i < np; i++) {
			for (int j = 0; j < np; j++) {
				minA[i][j] = -1;
				maxA[i][j] = -1;
			}
		}

		for (int start = 0; start < np; start++) {
			for (int des = 0; des < np; des++) {

				boolean[] visited = new boolean[np];
				Arrays.fill(visited, false);

				int moves = moves(start, des, 0, visited);
				if (moves == huge) moves = -1;

				System.out.print(moves + " ");

			}
			System.out.println();
		}

	}

	static int moves(int cur, int des, int nmoves, boolean[] visited) {

		if (cur == des) {
			return nmoves;
		}

		int min = huge;

		// loop through each set
		for (int i = 0; i < position[cur].length; i++) {

			int[] s = position[cur][i];
			int max = 0;

			// loop through each letter.
			for (int j = 0; j < s.length; j++) {

				int l = s[j];

				if (!visited[l]) {

					int temp;

					boolean[] visitedN = new boolean[visited.length];
					for (int k = 0; k < visited.length; k++) {
						visitedN[k] = visited[k];
					}

					visitedN[l] = true;

					temp = moves(l, des, nmoves + 1, visitedN);

					max = Math.max(temp, max);

				} else {

					max = huge;

				}

				//System.out.println(l + ", " + des + ", " + max + ", " + min + ", " + nmoves);

			}

			min = Math.min(max, min);

		}

		return min;

	}

}