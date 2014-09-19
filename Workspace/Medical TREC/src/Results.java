import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Results 
{
	public static void main(String args[])
	{
		
	}
	public static ArrayList<LineObject> results()
	{
		ArrayList<LineObject> lines = new ArrayList<LineObject>();
		try
		{
			File file = new File("src/2011Judgements.txt");
			Scanner scan = new Scanner(file);
			while(scan.hasNext())
			{
				String line = scan.nextLine();
				String [] tokens = line.split(" ");
				lines.add(new LineObject(tokens[0],tokens[2],tokens[3]));
			}
		}
		catch(FileNotFoundException fne)
		{
			System.out.println("File Not Found in Results");
		}
		return lines;
	}
	
}
