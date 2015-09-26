import java.util.*;

class Main
{

	// represent blocks with a 10x10 array. -1 means there is no block there.
	static int[][] blocks;
	static int numberOfBlocks;
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		numberOfBlocks = input.nextInt();
		blocks = new int[numberOfBlocks][numberOfBlocks];
		
		// Initialize all block spaces. Bottom column has blocks 0 - 9. The rest are -1, no block there..
		for (int row = 0; row < numberOfBlocks; row++)
		{
			for (int col = 0; col < numberOfBlocks; col++)
			{
				if (row == numberOfBlocks - 1)
				{
					blocks[row][col] = col;
				}
				else
				{
					blocks[row][col] = -1;
				}
			}
		}	
				
		while (true)
		{
			String command = input.next();
			
			if (command.contains("quit")) 
			{
				break;
			}
			
			if (command.contains("move"))
			{
				int a = input.nextInt();
				command = input.next();
				int b = input.nextInt();
				
				if (command.contains("over"))
				{
					moveOver(a, b);
				}
				if (command.contains("onto"))
				{
					moveOnto(a, b);
				}
			}
			
			if (command.contains("pile"))
			{
				int a = input.nextInt();
				command = input.next();
				int b = input.nextInt();
				
				if (command.contains("over"))
				{
					pileOver(a, b);
				}
				if (command.contains("onto"))
				{
					pileOnto(a, b);
				}
			}			
		}		
		
		printBlocks();
		
	}
	
	static void moveBlocksAtopToOriginalPosition(int blockNumber)
	{
	
		int row = find(blockNumber)[0];
		int col = find(blockNumber)[1];
		
		if (row == 0) 
		{
			return;
		}
		
		int k = 1;
		
		while (blocks[row - k][col] != -1) 
		{
			// there are blocks above, move them.
			moveToOriginalPosition(blocks[row - k][col]);
			k++;
		}
		
	}
	
	static void moveToOriginalPosition(int blockNumber)
	{
	
		int row = find(blockNumber)[0];
		int col = find(blockNumber)[1];
		
		blocks[row][col] = -1;
		blocks[numberOfBlocks - 1][blockNumber] = blockNumber;
	
	}
	
	static void moveOnto(int a, int b)
	{
		// move a onto b
		// Where a and b are block numbers, puts block a onto block b after returning any blocks that are stacked on top of blocks a and b to their initial positions.
		int aRow = find(a)[0];
		int aCol = find(a)[1];
		int bRow = find(b)[0];
		int bCol = find(b)[1];
		
		// do nothing if block a and b are in the same col.
		if (aCol == bCol)
		{
			return;
		}
		
		// check if there are blocks on top of a or b.
		moveBlocksAtopToOriginalPosition(a);
		moveBlocksAtopToOriginalPosition(b);
		
		// move block a on top of block b.
		blocks[bRow - 1][bCol] = a;
		blocks[aRow][aCol] = -1;
		
	}
	
	static void moveOver(int a, int b)
	{		
		// move a over b
		// Where a and b are block numbers, puts block a onto the top of the stack containing block b, after returning any blocks that are stacked on top of block a to their initial positions
				
		int aRow = find(a)[0];
		int aCol = find(a)[1];
		int bRow = find(b)[0];
		int bCol = find(b)[1];
		
		// do nothing if block a and b are in the same col.
		if (aCol == bCol)
		{
			return;
		}
		
		// check if there are blocks on top of a.
		moveBlocksAtopToOriginalPosition(a);

		// get the row of top most block in the column b is in.
		int topRow = bRow - 1;
		while (blocks[topRow][bCol] != -1)
		{
			topRow--;
		}
		
		// move block a on top of block b's stack.
		blocks[topRow][bCol] = a;
		blocks[aRow][aCol] = -1;

	}
	
	static void pileOnto(int a, int b)
	{
		// pile a onto b 
		// Where a and b are block numbers, moves the pile of blocks consisting of block a, and any blocks that are stacked above block a, onto block b. 
		// All blocks on top of block b are moved to their initial positions prior to the pile taking place. The blocks stacked above block a retain their order when moved.
	
		int aRow = find(a)[0];
		int aCol = find(a)[1];
		int bRow = find(b)[0];
		int bCol = find(b)[1];
		
		// do nothing if block a and b are in the same col.
		if (aCol == bCol)
		{
			return;
		}

		// check if there are blocks above b.
		moveBlocksAtopToOriginalPosition(b);
		
		int k = 0;
		while (blocks[aRow - k][aCol] != -1)
		{
			blocks[bRow - k - 1][bCol] = blocks[aRow - k][aCol];
			blocks[aRow - k][aCol] = -1;
			k++;
		}

	}
	
	static void pileOver(int a, int b)
	{
		// pile a over b
		// Where a and b are block numbers, puts the pile of blocks consisting of block a, and any blocks that are stacked above block a, onto the top of the stack containing block b. 
		// The blocks stacked above block a retain their original order when moved.
		
		int aRow = find(a)[0];
		int aCol = find(a)[1];
		int bRow = find(b)[0];
		int bCol = find(b)[1];

		// do nothing if block a and b are in the same col.
		if (aCol == bCol)
		{
			return;
		}
			
		// get the row of top most block in the column b is in.
		int topRow = bRow - 1;
		while (blocks[topRow][bCol] != -1)
		{
			topRow--;
		}
		
		int k = 0;
		while (blocks[aRow - k][aCol] != -1)
		{
			blocks[topRow - k][bCol] = blocks[aRow - k][aCol];
			blocks[aRow - k][aCol] = -1;
			k++;
		}


	}
	
	static int[] find(int blockNum)
	{
		// finds where a block number is. It returns an array where the first value is the row and the second value is the col.
		int[] returnArray = new int[2];
		
		for (int row = 0; row < numberOfBlocks; row++)
		{
			for (int col = 0; col < numberOfBlocks; col++)
			{
				if (blocks[row][col] == blockNum)
				{
					returnArray[0] = row;
					returnArray[1] = col;
					return returnArray;
				}				
			}
		}
		
		return returnArray;
	}
	
	static void printBlocks()
	{
		for (int col = 0; col < numberOfBlocks; col++)
		{
			for (int row = numberOfBlocks - 1; row > -1; row--)
			{
				if (row == numberOfBlocks - 1)
				{
					System.out.print(col + ":");
				}
				
				if (blocks[row][col] != -1)
				{
					System.out.print(" " + blocks[row][col]);
				}
			}
			System.out.print("\n");
		}
	}
	
}