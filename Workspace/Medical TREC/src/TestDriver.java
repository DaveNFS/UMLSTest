import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class TestDriver {
	final static String system = System.getProperty("user.name");
	public static void main(String args[]) throws IOException
	{
		
		
		
	}
	
	public static void createFile() throws IOException
	{
		Visit v = null;
		BufferedWriter out = null;
		try {
			v = new Visit();
			out = new BufferedWriter(new FileWriter("C:\\Users\\"+system+"\\Desktop\\Phrases"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<File> files = v.getAllFiles();
		ArrayList<String> tenWrds = new ArrayList<String>();
		HashMap<String,Integer> topPhrases = new HashMap<String,Integer>();
		String system = System.getProperty("user.name");
		
		
		for(File f: files)
		{
			File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\"+f.getName());
			Scanner scan = new Scanner(fo);

			while(scan.hasNext())
			{
				String line = scan.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(line);
				int count = 0;
				String addMe = "";
				while(tokenizer.hasMoreTokens() && count < 10)
				{
					count++;
					addMe = addMe + tokenizer.nextToken()+" ";
				}
				if(count == 10)
				{
					out.write(addMe);
					out.newLine();
				}
			}
		}		
	}
	 public static String removeNegation(String line)
	    {
	    	GenNegEx x = new GenNegEx(true);
			String scope = x.negScope(line);
			if(scope.equals("-1"))
				return line;
			else
			{
				String[] s = line.split("\\s+");
				String[] index = scope.split("\\s+");
				Integer start = Integer.parseInt(index[0]);
				Integer end = Integer.parseInt(index[2]);
				for(int z = start; z < end ; z++)
					s[z] = "";
				String edit = "";
				for(int y = 0; y < s.length ; y++)
					edit = edit + " "+s[y];
				return edit;
			}
	    }
}