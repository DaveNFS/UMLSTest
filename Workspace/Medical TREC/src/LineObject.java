
public class LineObject 
{
	public int topic;
	public String visitID;
	public int relevance;
	public LineObject(String top, String id, String rel)
	{
		topic = Integer.parseInt(top);
		visitID = id;
		relevance = Integer.parseInt(rel);
	}
	public String toString()
	{
		String temp = topic + " "+visitID+ "  "+relevance;
		return temp;
	}

}
