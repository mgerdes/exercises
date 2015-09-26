import java.util.*;

class Main
{
	
	static char[][] board;
	static boolean[][] visited;
	static int n;
	static boolean blackWins = false;
	
	public static void main(String[] args)
	{
	
		Scanner input = new Scanner(System.in);
		
		int k = 1;
		
		while (true)
		{
			blackWins = false;
			
			n = input.nextInt();
			
			if (n == 0) break;
			
			visited = new boolean[n][n];
			board = new char[n][n];
			
			for (int i = 0; i < n; i++)
			{
				String line = input.next();
				board[i] = line.toCharArray();
			}
			
			blackWon();
			
			System.out.print(k + (blackWins ? " B\n" : " W\n"));
			
			k++;	
		}
	}
	
	static void blackWon()
	{
		for (int col = 0; col < n; col++)
		{
			blackWon(0, col);
		}
	}
	
	static void blackWon(int row, int col) 
	{
		if (row == n - 1) 
		{
			blackWins = true;
		}
		else 
		{
			if (row > -1 && col > -1 && row < n && col < n) 
			{
				if (board[row][col] == 'b' && !visited[row][col]) 
				{
					visited[row][col] = true;
					
					blackWon(row - 1, col - 1);
					blackWon(row - 1, col);
					blackWon(row, col - 1);
					blackWon(row, col + 1);
					blackWon(row + 1, col);
					blackWon(row + 1, col + 1);
				}
			}
		}
	}
}
