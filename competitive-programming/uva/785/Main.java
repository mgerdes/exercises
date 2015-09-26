import java.util.*;

class Main {
	static char[][] contour;
	static boolean[][] visited;
	static int height;
	static int width;
	static char edgeCharacter;

	public static void main(String[] main) {
		Scanner input = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();

		while (input.hasNextLine()) {
			String line = input.nextLine();

			if (!line.contains("_")) {

				lines.add(line);
				height++;
				if (line.length() > width) width = line.length();

			} else {

				contour = new char[height][width];
				visited = new boolean[height][width];

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < lines.get(i).length(); j++) {
						contour[i][j] = lines.get(i).charAt(j);
					}
					for (int j = lines.get(i).length(); j < width; j++) {
						contour[i][j] = ' ';
					}
				}

				getEdgeCharacter();
				drawContour();

				for (int i = 0; i < height; i++) {
					System.out.println(new String(contour[i]).replaceAll("\\s+$", ""));
				}

				System.out.println(line);

				lines.clear();
				height = 0;
				width = 0;
			}
		}
	}

	static void getEdgeCharacter() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (contour[row][col] != ' ') {
					edgeCharacter = contour[row][col];
					return;
				}
			}
		}
	}

	static void drawContour() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (contour[row][col] != ' ')
					fill(row, col, contour[row][col]);
			}
		}
	}

	static void fill(int row, int col, char character) {
		if (row > -1 && col > -1 && row < height && col < width) {
			if (!visited[row][col] && contour[row][col] != edgeCharacter) {
				visited[row][col] = true;
				contour[row][col] = character;
				fill(row - 1, col, character);
				fill(row, col + 1, character);
				fill(row + 1, col, character);
				fill(row, col - 1, character);
			}
		}
	}
}