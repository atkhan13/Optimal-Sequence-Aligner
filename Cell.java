//Class to represent each cell. Cells contain three values which we store using this Cell class.
//Additionally we store which nucleotide is represented by each sequence in this cell as well as which direction
// resulted in the maximum score which is then used for traceback.
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
