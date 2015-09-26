import java.util.*;

class Main {
	static final int MAX_ROWS = 15, MAX_COLUMNS = 115;

	static Scanner input = new Scanner(System.in);
	static int numOfColumns, numOfRows, 
			   array[][] = new int[MAX_ROWS][MAX_COLUMNS],
			   minPath[][] = new int[MAX_ROWS][MAX_COLUMNS],
			   prevRow[][] = new int[MAX_ROWS][MAX_COLUMNS],
			   minimumWeight, minimumStartRow;

	public static void main(String[] args) {
		while(input.hasNext()) {
			getInputForCase();
			getMinimumWeight();
			printResults();
		}
	}

	public static void printResults() {
		int row = minimumStartRow;
		int col = 0;
		while (prevRow[row][col] != -1) {
			System.out.print(row + 1);
			row = prevRow[row][col];
			col++;
			System.out.print(" ");
		}
		System.out.println(row + 1);
		System.out.println(minimumWeight);
	}

	public static void getMinimumWeight() {
		for (int columnNum = numOfColumns - 1; columnNum >= 0; columnNum--) {
			for (int rowNum = numOfRows - 1; rowNum >= 0; rowNum--) {
				if (columnNum == numOfColumns - 1) { 
					minPath[rowNum][columnNum] = array[rowNum][columnNum];	
					prevRow[rowNum][columnNum] = -1;
				} else {
					int path1, path2, path3, minimumPath, path;

					minPath[rowNum][columnNum] = Integer.MAX_VALUE;

					if (rowNum - 1 < 0) {
						path = minPath[numOfRows - 1][columnNum + 1] + array[rowNum][columnNum];
						if (path <= minPath[rowNum][columnNum]) {
							minPath[rowNum][columnNum] = path;
							prevRow[rowNum][columnNum] = numOfRows - 1;
						}
					}

					if (rowNum + 1 < numOfRows) {
						path = minPath[rowNum + 1][columnNum + 1] + array[rowNum][columnNum];
						if (path <= minPath[rowNum][columnNum]) {
							minPath[rowNum][columnNum] = path;
							prevRow[rowNum][columnNum] = rowNum + 1;
						}
					}

					path = minPath[rowNum][columnNum + 1] + array[rowNum][columnNum];
					if (path <= minPath[rowNum][columnNum]) {
						minPath[rowNum][columnNum] = path;
						prevRow[rowNum][columnNum] = rowNum;
					}

					if (rowNum - 1 >= 0) {
						path = minPath[rowNum - 1][columnNum + 1] + array[rowNum][columnNum];
						if (path <= minPath[rowNum][columnNum]) {
							minPath[rowNum][columnNum] = path;
							prevRow[rowNum][columnNum] = rowNum - 1;
						}
					}

					if (rowNum + 1 >= numOfRows) {
						path = minPath[0][columnNum + 1] + array[rowNum][columnNum];	
						if (path <= minPath[rowNum][columnNum]) {
							minPath[rowNum][columnNum] = path;
							prevRow[rowNum][columnNum] = 0;
						}
					}

				}
				if (columnNum == 0) {
					if (rowNum == numOfRows - 1) {
						minimumWeight = minPath[rowNum][columnNum];
						minimumStartRow = rowNum;
					}  
					if (minPath[rowNum][columnNum] <= minimumWeight) {
						minimumWeight = minPath[rowNum][columnNum];
						minimumStartRow = rowNum;
					}
				}
			}
		}
	}

	public static void getInputForCase() {
		numOfRows = input.nextInt();	
		numOfColumns = input.nextInt();
		for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
			for (int columnNum = 0; columnNum < numOfColumns; columnNum++) {
				array[rowNum][columnNum] = input.nextInt();
			}
		}
	}

	public static void printArray() {
		for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
			for (int columnNum = 0; columnNum < numOfColumns; columnNum++) {
				System.out.print(array[rowNum][columnNum]);
			}
			System.out.println();
		}
	}

	public static int minimum(int one, int two, int three) {
		return Math.min(Math.min(one,two), three);
	}

}
