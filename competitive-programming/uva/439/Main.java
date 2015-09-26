import java.util.*;

class Move {
	int row;
	int col;
	int moves;
	Move(int row, int col, int moves) {
		this.row = row;
		this.col = col;
		this.moves = moves;
	}
}

class Main {
	static boolean[][] visited = new boolean[8][8];

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		while (input.hasNext()) {
			String source = input.next();
			String dest = input.next();

			int moves = moves(source, dest);

			//set visited to false;
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					visited[row][col] = false;
				}
			}

			//To get from e2 to e4 takes 2 knight moves.
			System.out.printf("To get from %s to %s takes %d knight moves.", source, dest, moves);

			System.out.println();
		}
	}

	static int moves(String source, String dest) {
		LinkedList<Move> q = new LinkedList<Move>();

		int rowS = source.charAt(0) - 'a';		//source row
		int colS = source.charAt(1) - '0' - 1;	//source col
		int rowD = dest.charAt(0) - 'a';	  	//dest row
		int colD = dest.charAt(1) - '0' - 1;	//dest col

		q.add(new Move(rowS, colS, 0));

		while (!q.isEmpty()) {
			Move current = q.poll();

			int rowC = current.row; 		//current row
			int colC = current.col; 		//current col
			int movesC = current.moves;	    // current moves

			if (rowC > -1 && rowC < 8 && colC > -1 && colC < 8) {
				if (!visited[rowC][colC]) {
					//System.out.println("Moving to " + rowC + ", " + colC);

					visited[rowC][colC] = true;

					if (rowC == rowD && colC == colD) return movesC;

					q.add(new Move(rowC - 2, colC - 1, movesC + 1));
					q.add(new Move(rowC - 2, colC + 1, movesC + 1));
					q.add(new Move(rowC - 1, colC - 2, movesC + 1));
					q.add(new Move(rowC - 1, colC + 2, movesC + 1));
					q.add(new Move(rowC + 1, colC - 2, movesC + 1));
					q.add(new Move(rowC + 1, colC + 2, movesC + 1));
					q.add(new Move(rowC + 2, colC - 1, movesC + 1));
					q.add(new Move(rowC + 2, colC + 1, movesC + 1));
				}
			}
		}

		return -1;
	}
}