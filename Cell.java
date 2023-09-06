import java.util.ArrayList;
public class Cell {
	public int mValue;
	public int xValue;
	public int yValue;
	public char sLetter;
	public char tLetter;
	public ArrayList<String> mDirections = new ArrayList<String>();
	
	public Cell(int mValue, int xValue, int yValue, char sLetter, char tLetter)
	{
		this.mValue = mValue;
		this.xValue = xValue;
		this.yValue = yValue;
		this.sLetter = sLetter;
		this.tLetter = tLetter;
	}
	
	public int getmValue()
	{
		return this.mValue;
	}
	public int getxValue()
	{
		return this.xValue;
	}
	public int getyValue()
	{
		return this.yValue;
	}
	public char getsLetter()
	{
		return this.sLetter;
	}
	public char gettLetter()
	{
		return this.tLetter;
	}

}
