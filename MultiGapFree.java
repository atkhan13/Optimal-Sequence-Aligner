import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class MultiGapFree {
	public static void main(String [] args)
	{
		File file = new File("short");
		ArrayList<String> strings = optimalAlignment(file, 1, -1, -1);
		int optimalScore = optimalScore(strings.get(0), strings.get(1), 1, -1, -1);
		System.out.println(strings.get(0));
		for(int i = 0; i < strings.get(0).length(); i ++)
		{
			System.out.print("|");
		}
		System.out.println();
		System.out.println(strings.get(1));
		System.out.println("Alignment score: " +optimalScore);
	}
	
	public static ArrayList<String> optimalAlignment(File file, int matchScore, int mismatchScore, int penalty)
	{
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> myStrings = new ArrayList<String>();
		while(sc.hasNext())
		{
			String seq = sc.next();
			if(seq.contains(">") || seq.isEmpty())
			{
				continue;
			}
			myStrings.add(seq);
		}
		String s = myStrings.get(0);
		String t = myStrings.get(1);
		int n = s.length();
		int m = t.length();
		Cell[][] myMatrix = new Cell[n+1][m+1];
		Cell initialCell = new Cell(0, penalty, penalty, '-', '-');
		myMatrix[0][0] = initialCell;
		for(int j = 1; j <= m; j++)
		{
			if(j == 1)
			{
				Cell add = new Cell(-10000, -10000, penalty, '-', t.charAt(j-1));
				myMatrix[0][j] = add;
			}
			else {
				int mValue = -10000;
				int yValue = -10000;
				int xValue = -10000;
				Cell add = new Cell(mValue, xValue, yValue, '-', t.charAt(j-1));
				myMatrix[0][j] = add;
			}
		}
		for(int i = 1; i <= n; i++)
		{
			if(i == 1)
			{
				Cell add = new Cell(-10000, penalty, -10000,s.charAt(i-1), '-');
				myMatrix[i][0] = add;
			}
			else {
				int mValue = -10000;
				int yValue = -10000;
				int xValue = -10000;
				Cell add = new Cell(mValue, xValue, yValue, s.charAt(i-1), '-');
				myMatrix[i][0] = add;
			}
		}
		for(int i = 1; i < n + 1; i ++)
		{
			for(int j = 1; j < m + 1; j++)
			{
				int sub = 0;
				if(myMatrix[i-1][j].gettLetter() == (myMatrix[i][j-1].getsLetter()))
				{
					sub = matchScore;
				}
				else
				{
					sub = mismatchScore;
				}
				int m1 = myMatrix[i-1][j-1].getmValue() + sub;
				int m2 = myMatrix[i-1][j-1].getxValue() + sub;
				int m3 = myMatrix[i-1][j-1].getyValue() + sub;
				int mValue = Math.max(Math.max(m1, m2), m3);
				int xValue = myMatrix[i-1][j].getmValue() + penalty;
				int yValue = myMatrix[i][j-1].getmValue() + penalty;
				Cell toAdd = new Cell(mValue, xValue, yValue, myMatrix[i][j-1].getsLetter(), myMatrix[i-1][j].gettLetter());
				if(m1 == mValue)
				{
					toAdd.mDirections.add("m");
				}
				if(m2 == mValue)
				{
					toAdd.mDirections.add("x");
				}
				if(m3 == mValue)
				{
					toAdd.mDirections.add("y");
				}
				myMatrix[i][j] = toAdd;
			}
		}
		int i = n;
		int j = m;
		String newS = "";
		String newT = "";
		int myMax = Math.max(Math.max(myMatrix[n][m].getmValue(), myMatrix[n][m].getxValue()), myMatrix[n][m].getyValue());
		String op;
		if(myMax == myMatrix[n][m].getmValue())
		{
			op = "m";
		}
		else if(myMax == myMatrix[n][m].getxValue())
		{
			op = "x";
		}
		else
		{
			op = "y";
		}
		while(i != 0 || j != 0)
		{
			if (op.equals("m"))
			{
				newS = myMatrix[i][j].getsLetter() + newS;
				newT = myMatrix[i][j].gettLetter() + newT;
				op = myMatrix[i][j].mDirections.get(0);
				i = i - 1;
				j = j -1;
			}
			else if(op.equals("x"))
			{
				newS = myMatrix[i][j].getsLetter() + newS;
				newT = "-" + newT;
				i = i - 1;
				op = "m";
			}
			else
			{
				newS = "-" + newS;
				newT = myMatrix[i][j].gettLetter() + newT;
				op = "m";
				j = j - 1;
			}
			
		}
		ArrayList<String> myAlignment = new ArrayList<String>();
		myAlignment.add(newT);
		myAlignment.add(newS);
		return myAlignment;
	}
	
	public static int optimalScore(String s, String t, int match, int mismatch, int penalty)
	{
		int score = 0;
		for(int i = 0 ; i < s.length(); i ++)
		{
			if(s.charAt(i) == t.charAt(i))
			{
				score += match;
			}
			else if(s.charAt(i) == '-' || t.charAt(i) == '-')
			{
				score += penalty;
			}
			else {
				score += mismatch;
			}
		}
		return score;
	}
	
	

}
