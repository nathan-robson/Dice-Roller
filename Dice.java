package Workspace;

import java.util.Random;

public class Dice // Class and class methods for Die construction
{
		// die information
		private int result;
		private int numSides;
		
		
		// Die constructor
		Dice(int sides)
		{
			if(sides > 1)
			{
				result = new Random().nextInt(sides) + 1;
				numSides = sides;
			}
			else
			{
				System.err.println("Invalid number of die sides, defaulting to 2");
				result = new Random().nextInt(2) + 1;
				numSides = 2;
			}
		}
		
		// Default die constructor
		Dice()
		{
			result = new Random().nextInt(6) + 1;
			numSides = 6;
		}
		
		public Dice(String strNumSides) 
		{
			Integer num = Integer.parseInt(strNumSides);
			
			if(num != null)
			{
				result = new Random().nextInt(num) + 1;
			}			
		}

		// accessors
		public int getResult() { return result; }
		public int getSides() { return numSides; }

}
