import java.util.*;

class Main
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		ArrayList<int[][]> polynomials = new ArrayList<int[][]>();
		
		while (true)
		{
			String polynomialString = input.nextLine();
			if (polynomialString.equals("#"))
			{
				break;
			}
			char[] polynomialChar = polynomialString.toCharArray();
			
			// find the number of terms.
			String[] tokens = polynomialString.split("[+-]"); 
				
			int numOfTerms = tokens.length;
			
			if (tokens[0].equals(""))
			{
				numOfTerms--;
			}
			// finished finding number of terms
							
			int[][] terms = new int[numOfTerms][4];
			
			// find the signs of each term.
			if (polynomialChar[0] == '-' )
			{
				terms[0][0] = -1;
			}
			else
			{
				terms[0][0] = 1;
			}
			int term = 1;
			for (int i = 1; i < polynomialChar.length; i++)
			{
				if (polynomialChar[i] == '+')
				{
					terms[term][0] = 1;
					term++;
				}
				if (polynomialChar[i] == '-')
				{
					terms[term][0] = -1;
					term++;
				}
			}
			// finished finding the signs of each term.
			
			// find the coefficients and exponents of each term.
			term = 0;
			for (int i = 0; i < tokens.length; i++)
			{
				if (tokens[i].equals(""))
				{
					continue;
				}
				
				char[] tokensString = tokens[i].toCharArray();
				String coefficient = "";
				String xExponent = "";
				String yExponent = "";
				int k;
				
				if (tokensString[0] == 'x' || tokensString[0] == 'y')
				{
					terms[term][1] = terms[term][0] * 1;
					k = 1;
				}
				else
				{
					k = 0;
					while(k < tokensString.length && Character.isDigit(tokensString[k]))
					{
						coefficient += tokensString[k];
						k++;
					}
					terms[term][1] = terms[term][0] * Integer.parseInt(coefficient);
				}
				
				if (!tokens[i].contains("x"))
				{
					terms[term][2] = 0;
				}
				else
				{
					k = tokens[i].indexOf('x') + 1;
					while(k < tokensString.length && Character.isDigit(tokensString[k]))
					{
						xExponent += tokensString[k];
						k++;
					}
					if (xExponent.equals(""))
					{
						xExponent = "1";
					}
					terms[term][2] = Integer.parseInt(xExponent);
				}
				
				if (!tokens[i].contains("y"))
				{
					terms[term][3] = 0;
				}
				else
				{
					k = tokens[i].indexOf('y') + 1;
					while(k < tokensString.length && Character.isDigit(tokensString[k]))
					{
						yExponent += tokensString[k];
						k++;
					}
					if (yExponent.equals(""))
					{
						yExponent = "1";
					}
					terms[term][3] = Integer.parseInt(yExponent);

				}
				term++;
			}
			// finished finding the coefficients and exponents of each term.
			
			
			// test terms array.
			/*
			for (int i = 0; i < numOfTerms; i++)
			{
				System.out.println(terms[i][0] + ", " + terms[i][1] + ", " + terms[i][2] + ", " + terms[i][3]);
			}
			*/
			
			polynomials.add(terms);
		}
		
		
		
		int[][] newPolynomial = new int[0][0];
		for (int q = 0; q < polynomials.size() - 1; q += 2)
		{
			int[][] polynomial1 = polynomials.get(q);
			int[][] polynomial2 = polynomials.get(q + 1);
				
			int numOfTerms1 = polynomial1.length;
			int numOfTerms2 = polynomial2.length;

			newPolynomial = new int[numOfTerms1 * numOfTerms2][4];
				
			int l = 0;
			for (int j = 0; j < numOfTerms1; j++)
			{
				for (int k = 0; k < numOfTerms2; k++)
				{
					newPolynomial[l][1] = polynomial1[j][1] * polynomial2[k][1];
					newPolynomial[l][2] = polynomial1[j][2] + polynomial2[k][2];
					newPolynomial[l][3] = polynomial1[j][3] + polynomial2[k][3];
					l++;
				}
			}
			
			// combine like terms
			for (int i = 0; i < newPolynomial.length; i++)
			{
				int xExponent1 = newPolynomial[i][2];
				int yExponent1 = newPolynomial[i][3];
				for (int j = i + 1; j < newPolynomial.length; j++)
				{
					int xExponent2 = newPolynomial[j][2];
					int yExponent2 = newPolynomial[j][3];
					if (xExponent1 == xExponent2 && yExponent1 == yExponent2)
					{
						newPolynomial[i][1] += newPolynomial[j][1];
						newPolynomial[j][1] = 0;
					}
				}
			}

			// sort the polynomial1
			for (int i = 0; i < newPolynomial.length; i++)
			{
				for (int j = i + 1; j < newPolynomial.length; j++)
				{
					int coefficient1 = newPolynomial[i][1];
					int xExponent1 = newPolynomial[i][2];
					int yExponent1 = newPolynomial[i][3];
				
					int coefficient2 = newPolynomial[j][1];
					int xExponent2 = newPolynomial[j][2];
					int yExponent2 = newPolynomial[j][3];		
					
					if (xExponent2 > xExponent1 && coefficient1 != 0 && coefficient2 != 0)
					{
						newPolynomial[i][1] = coefficient2;
						newPolynomial[i][2] = xExponent2;
						newPolynomial[i][3] = yExponent2;
						
						newPolynomial[j][1] = coefficient1;
						newPolynomial[j][2] = xExponent1;
						newPolynomial[j][3] = yExponent1;
					}
				}
			}
			
			for (int i = 0; i < newPolynomial.length; i++)
			{			
				for (int j = i + 1; j < newPolynomial.length; j++)
				{
					int coefficient1 = newPolynomial[i][1];
					int xExponent1 = newPolynomial[i][2];
					int yExponent1 = newPolynomial[i][3];
				
					int coefficient2 = newPolynomial[j][1];
					int xExponent2 = newPolynomial[j][2];
					int yExponent2 = newPolynomial[j][3];	
					
					if (xExponent2 == xExponent1 && yExponent1 > yExponent2 && coefficient1 != 0 && coefficient2 != 0)
					{
						newPolynomial[i][1] = coefficient2;
						newPolynomial[i][2] = xExponent2;
						newPolynomial[i][3] = yExponent2;
						
						newPolynomial[j][1] = coefficient1;
						newPolynomial[j][2] = xExponent1;
						newPolynomial[j][3] = yExponent1;
					}
				}
			}
			
			for (int i = 0; i < newPolynomial.length; i++)
			{
				if(newPolynomial[i][1] < 0)
				{
					newPolynomial[i][0] = -1;
				}
				else
				{
					newPolynomial[i][0] = 1;
				}
				newPolynomial[i][1] = Math.abs(newPolynomial[i][1]);
			}
			
			// print the polynomial
			String exponents = "";
			String coefficients = "";
			for (int i = 0; i < newPolynomial.length; i++)
			{
				if (newPolynomial[i][1] != 0)
				{
					// add sign
					if (i > 0)
					{
						coefficients += " ";
						exponents += " ";
					}
					if (newPolynomial[i][0] < 0)
					{
						coefficients += "-";
						exponents += " ";
					}
					else if (i > 0)
					{
						coefficients += "+";
						exponents += " ";
					}
					if (i > 0)
					{
						coefficients += " ";
						exponents += " ";
					}
					
					// add coefficient
					if(newPolynomial[i][1] != 1)
					{
						coefficients += newPolynomial[i][1];
						exponents += Integer.toString(newPolynomial[i][1]).replaceAll("[0-9]", " ");
					}
					if (newPolynomial[i][2] == 0 && newPolynomial[i][3] == 0 && newPolynomial[i][1] == 1)
					{
						coefficients += newPolynomial[i][1];
						exponents += " ";
					}
									
					// add x and y exponents
					if (newPolynomial[i][2] != 0)
					{
						coefficients += "x";
						exponents += " ";
					}
					if (newPolynomial[i][2] != 1 && newPolynomial[i][2] != 0)
					{
						exponents += newPolynomial[i][2];
						coefficients += Integer.toString(newPolynomial[i][2]).replaceAll("[0-9]", " ");
					}
					
					if (newPolynomial[i][3] != 0)
					{
						coefficients += "y";
						exponents += " ";
					}
					if (newPolynomial[i][3] != 1 && newPolynomial[i][3] != 0)
					{
						exponents += newPolynomial[i][3];
						coefficients += Integer.toString(newPolynomial[i][3]).replaceAll("[0-9]", " ");
					}
				}
			}
			System.out.println(exponents);
			System.out.println(coefficients);	
		}		
	}
}