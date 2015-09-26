import java.util.*;

class Main
{
	static int squares;
	static int x;
	static int y;
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		while (true)
		{
			int k = input.nextInt();
			x = input.nextInt();
			y = input.nextInt();
			squares = 0;
			
			if (k == 0 || x == 0 || y == 0) break;
			
			inSquares(1024, 1024, k);
			
			System.out.printf("%3d\n", squares);
		}
				
	}
	
	static void inSquares(int centerX, int centerY, int k)
	{
		//System.out.println(centerX + ", " + centerY + ", " + k);
		
		if (x <= centerX + k && x >= centerX - k && y <= centerY + k && y >= centerY - k )
		{
			squares++;
		}
		if (k == 1)
		{
			return;
		}
		
		inSquares(centerX + k, centerY + k, k / 2);
		inSquares(centerX + k, centerY - k, k / 2);
		inSquares(centerX - k, centerY + k, k / 2);
		inSquares(centerX - k, centerY - k, k / 2);
	
	}

}