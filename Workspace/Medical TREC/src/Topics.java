import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.Iterator;


public class Topics 
{
	private static HashMap<Integer,String> tops;
	public Topics()
	{
		tops = new HashMap<Integer,String>();
		try
		{
			File f = new File("src/topics2011.txt");
			Scanner scan = new Scanner(f);
			while(scan.hasNext())
			{
				
				String line = scan.nextLine();
				System.out.println(line);
				StringTokenizer tokenizer = new StringTokenizer(line, " ");
				Integer i = Integer.parseInt(tokenizer.nextToken()); 
				String s = "";
				while(tokenizer.hasMoreTokens())
					s = s + tokenizer.nextToken() + " ";
				tops.put(i, s);
			}
		}
		catch(IOException ioe)
		{
			System.out.println("Topics2011 not found");
		}
	}
	public HashMap<Integer,String > getTopics()
	{
		return tops;
	}
}
