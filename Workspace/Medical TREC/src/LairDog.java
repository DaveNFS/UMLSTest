import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LairDog 
{
	//public static Visit v;
	public static void main(String args[]) throws FileNotFoundException
	{
		Visit v = new Visit();
		ArrayList<File> files = v.getAllFiles();
		ArrayList<String> words = getWords();
		String system = System.getProperty("user.name");
		String title = "Words     ";
		for(String w : words)
		{
			title = title + "  " + w;
		}
		System.out.println(title);
		
		for(File f : files)
		{
			File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\"+f.getName());
			Scanner scan = new Scanner(fo);
			int wordNum = 0;
			String report = f.getName();
			String print = report + "       ";
			ArrayList<Integer> wrdCounts = new ArrayList<Integer>();
			for(String w: words)
				wrdCounts.add(0);
			while(scan.hasNext())
			{
				String line = scan.nextLine();
				if(line.contains("."))
					line = line.replace('.' ,' ');
				if(line.contains("."))
					line = line.replace(',' ,' ');
				String [] term = line.split(" ");
				for(int x = 0; x < term.length; x++)
				{
					for(int y = 0 ; y < words.size(); y++)
					{						
						if(term[x].equalsIgnoreCase(words.get(y)))
						{
							wordNum++;
							wrdCounts.set(y, wrdCounts.get(y) + 1);
						}
					}
				}
			}
			if(wordNum > 7)
				System.out.println(print + wrdCounts);
			
		}
		
	}
	/*
	public static void bullshit() throws FileNotFoundException
	{
		ArrayList<File> files = v.getAllFiles();
		ArrayList<String> words = getWords();
		String system = System.getProperty("user.name");
		String title = "Words     ";
		for(String w : words)
		{
			title = title + "  " + w;
		}
		System.out.println(title);
		File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\report10030.xml");
		Scanner scan = new Scanner(fo);
		int wordNum = 0;
		String report = fo.getName();
		String print = report + "       ";
		ArrayList<Integer> wrdCounts = new ArrayList<Integer>();
		for(String w: words)
			wrdCounts.add(0);
		while(scan.hasNext())
		{
			String line = scan.nextLine();
			if(line.contains("."))
				line = line.replace('.' ,' ');
			if(line.contains("."))
				line = line.replace(',' ,' ');
			String [] term = line.split(" ");
			for(int x = 0; x < term.length; x++)
			{
				

				for(int y = 0 ; y < words.size(); y++)
				{						
					if(term[x].equalsIgnoreCase(words.get(y)))
					{
						System.out.println(term[x]);
						wordNum++;
						wrdCounts.set(y, wrdCounts.get(y) + 1);
					}
				}
			}
		}
		if(wordNum > 6)
			System.out.println(print + wrdCounts);
		
	}
	*/
	public static ArrayList<String> getWords() throws FileNotFoundException
	{
		ArrayList<String> temp = new ArrayList<String>();
		File f = new File("src/larrywrds.txt");
		Scanner sc = new Scanner(f);
		while(sc.hasNext())
			temp.add(sc.nextLine());
		return temp;
	}

}
