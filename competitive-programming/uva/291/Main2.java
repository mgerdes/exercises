import java.util.*;

class Main
{
	static int[][] house = new int[6][6];
	static int[][] p = {{},{2,3,5},{1,3,5},{1,2,4,5},{3,5},{1,2,3,4}};
	
	static ArrayList<Integer> solution = new ArrayList<Integer>();
		
	public static void main(String[] args)
	{
		solution.add(1);
		drawLine(1);
	}
	
	static void drawLine(int a)
	{
		for (int i = 0; i < p[a].length; i++)
		{		
			int b = p[a][i];
			
			if (house[a][b] == 0 && house[b][a] == 0)
			{
				house[a][a] = 1;
				house[b][a] = 1;
				solution.add(b);

				if (solution.size() == 9) 
				{
					for (int j = 0; j < 9; j++)
					{
						System.out.print(solution.get(j));
					}
					System.out.print("\n");
				}
				else 
				{
					drawLine(b);
				}

				house[a][b] = 0;
				house[b][a] = 0;
				solution.remove(solution.size() - 1);
			}
		}
	}
}
