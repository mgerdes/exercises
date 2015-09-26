import java.util.*;

class Main {
	static char[][] field;
	static boolean[][] visited;
	static int[][] depth;
	static int m;
	
	public static void main(String[] args) {
	
		Scanner input = new Scanner(System.in);
		
		while (input.hasNext()) {
		
			m = input.nextInt();
			
			field = new char[m][m];
			visited = new boolean[m][m];
			depth = new int[m][m];
				
			for (int i = 0; i < m; i++) {
				field[i] = input.next().toCharArray();
			}
			
			System.out.println(bfs());
			
		}
		
	}
	
	static int bfs() {
	
		LinkedList<int[]> q = new LinkedList<int[]>();
		
		int maxDepth = 0;
		
		for (int i = 0; i < m; i++){
			for (int j = 0; j < m; j++) {
				if (field[i][j] == '1') {
			
					q.clear();
								
					q.add(new int[]{i, j});
					
					depth[i][j] = 0;
					int curDepth = 0;
					
					for (int k = 0; k < m; k++)
						for (int l = 0; l < m; l++) {
							visited[k][l] = false;
						}
					
					while(!q.isEmpty()) {
					
						int[] cur = q.poll();
						int curRow = cur[0];
						int curCol = cur[1];
						curDepth = depth[curRow][curCol];
												
						if (!visited[curRow][curCol]) {
												
							if (field[curRow][curCol] == '3') 
								break;
								
							visited[curRow][curCol] = true;
								
							if (curRow - 1 > -1) {
								q.add(new int[]{curRow - 1, curCol});
								depth[curRow - 1][curCol] = curDepth + 1;
							}
							if (curRow + 1 < m)  {
								q.add(new int[]{curRow + 1, curCol});
								depth[curRow + 1][curCol] = curDepth + 1;
							}
							if (curCol - 1 > -1) {
								q.add(new int[]{curRow, curCol - 1});
								depth[curRow][curCol - 1] = curDepth + 1;
							}
							if (curCol + 1 < m) {
								q.add(new int[]{curRow, curCol + 1});
								depth[curRow][curCol + 1] = curDepth + 1;
							}
							
						}
					}
					
					if (curDepth > maxDepth) maxDepth = curDepth;
						
				}
					
			}
		}
		return maxDepth;
	}
	
}