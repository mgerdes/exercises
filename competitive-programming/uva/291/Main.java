import java.util.*;

class Main
{

	static int[][] house = new int[6][6];
	static int[][] p = {{},{2,3,5},{1,3,5},{1,2,4,5},{3,5},{1,2,3,4}};
	
	public static void main(String[] args)
	{			
		for (int a = 0; a < 4; a++)
		{
			for (int b = 0; b < 4; b++)
			{
				for (int c = 0; c < 4; c++)
				{
					for (int d = 0; d < 4; d++)
					{
						for (int e = 0; e < 4; e++)
						{
							for (int f = 0; f < 4; f++)
							{
								for (int g = 0; g < 4; g++)
								{
									for (int h = 0; h < 4; h++)
									{
										
										int first = 1;
										
										if(a >= p[first].length) continue;
										int second = p[first][a];
										
										if (b >= p[second].length) continue;
										int third = p[second][b];
										
										if (c >= p[third].length) continue;
										int fourth = p[third][c];
										
										if (d >= p[fourth].length) continue;
										int fifth = p[fourth][d];
										
										if (e >= p[fifth].length) continue;
										int sixth = p[fifth][e];
										
										if (f >= p[sixth].length) continue;
										int seventh = p[sixth][f];
										
										if (g >= p[seventh].length) continue;
										int eigth = p[seventh][g];
										
										if (h >= p[eigth].length) continue;
										int ninth = p[eigth][h];
										
										initializeHouse();
										
										if(!draw(first, second)) continue; //1
										if(!draw(second, third)) continue; //2
										if(!draw(third, fourth)) continue; //3
										if(!draw(fourth, fifth)) continue; //4
										if(!draw(fifth, sixth)) continue; //5
										if(!draw(sixth, seventh)) continue; //6
										if(!draw(seventh, eigth)) continue; //7
										if(!draw(eigth, ninth)) continue; //8
										
										System.out.println(first + "" + second + "" + third + "" + fourth + "" + fifth + "" + sixth + "" + seventh + "" + eigth + "" + ninth);
									}
								}
							}
						}
					}
				}
			}
		}	
	}
	
	static boolean draw(int a, int b)
	{
		if(house[a][b] == 0 && house[b][a] == 0)
		{
			house[a][b] = 1;
			house[b][a] = 1;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static void initializeHouse()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				house[i][j] = 0;
			}
		}
	}
}
