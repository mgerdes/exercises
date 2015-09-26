import java.util.*;

class Main
{

	static ArrayList<Integer> results = new ArrayList<Integer>();

	public static void main(String[] args)
	{
	
		Scanner in = new Scanner(System.in);
		
		while(true)
		{
			results.clear();
			
			int num = in.nextInt();
			
			if(num == 0)
			{
				break;
			}
			
			System.out.println("Original number was " + num);
			
			numberChain(num);
			
			System.out.println("Chain length " + (results.size() + 1) + "\n");
		}
	
	}
	
	static void numberChain(int num)
	{
		int D = sort(num, 'd');
		int A = sort(num, 'a');
		int R = D - A;
		
		System.out.println(D + " - " + A + " = " + R);
		
		if(results.contains(R))
		{
			return;
		}
		else
		{
			results.add(R);
			numberChain(R);
		}
	}
	
	static int sort(int num, char order)
	{
		char[] numChar = Integer.toString(num).toCharArray();
		
		for(int i = 0; i < numChar.length; i++)
		{
			for(int j = i + 1; j < numChar.length; j++)
			{
				if (order == 'a') // ascending order
				{
					if(numChar[i] > numChar[j])
					{
						char temp = numChar[i];
						numChar[i] = numChar[j];
						numChar[j] = temp;
					}
				}
				if (order == 'd') // descending order
				{
					if(numChar[i] < numChar[j])
					{
						char temp = numChar[i];
						numChar[i] = numChar[j];
						numChar[j] = temp;
					}
				}
			}
		}
		
		String numString = "";
		
		for (int i = 0; i < numChar.length; i++)
		{
			numString += numChar[i];
		}
		
		return Integer.parseInt(numString);
	}
}