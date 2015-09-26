import java.util.*;

class Main
{

	static boolean[][] visited;
	static char[][] picture;

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);	
		
		int k = 1;
		
		while (input.hasNextInt())
		{
			int n = input.nextInt();
			
			picture = new char[n][n];
			visited = new boolean[n][n];
			
			for (int row = 0; row < n; row++)
			{
				picture[row] = input.next().toCharArray();
			}
			
			System.out.printf("Image number %d contains %d war eagles.\n", k, countWarEagles());
			
			k++;
			
		}
	
	}
	
	static int countWarEagles()
	{
		int n = 0;
		
		for (int row = 0; row < picture.length; row++)
		{
			for (int col = 0; col < picture.length; col++)
			{
				if (picture[row][col] == '1' && !visited[row][col])
				{
					n++;
					countWarEagles(row, col);
				}
			}
		}

		return n;
	}
	
	static void countWarEagles(int row, int col)
	{
		int n = 0;

		if (row > -1 && row < picture.length && col > -1 && col < picture.length)
		{
			if (picture[row][col] == '1' && !visited[row][col])
			{
				visited[row][col] = true;
				countWarEagles(row - 1, col - 1);
				countWarEagles(row - 1, col);
				countWarEagles(row - 1, col + 1);
				countWarEagles(row, col - 1);
				countWarEagles(row, col + 1);
				countWarEagles(row + 1, col - 1);
				countWarEagles(row + 1, col);
				countWarEagles(row + 1, col + 1);
			}	
		}
	}
	
}
