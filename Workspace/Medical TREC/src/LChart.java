import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Iterator;

/*
 * Class currently checks Topic 119 against all relevant visit Id's and
 * runs a relevancey test that Professor Medsker wanted us to run. 
 */
public class LChart 
{
	public static final String system = System.getProperty("user.name");
	public static ArrayList<LineObject> results = Results.results();
	//public static ArrayList<LineObject> visits = getVisits(results);
	private static HashMap <String,Integer> IDrank;
	//visitIds and standard deviation thing
	private static HashMap <String,Double> std;
	//visitIds and arraylist<integer> with keyword totals
	private static HashMap <String, ArrayList<Integer>> keywords;
	private static HashMap <String, VisitInfo> information;
	private static HashMap<String,ArrayList<File>> reports;
	private static int correctCount;
	private static int totalCount;
	private static int correctCount2;
	private static int totalCount2;
	private static HashMap <Integer, ArrayList<NeuralObject>> larry;
	private static ArrayList<NeuralObject> larryLines;
	public LChart() throws Exception
	{
		Visit v = new Visit();
		NeuralNetworkCleaner n = new NeuralNetworkCleaner();
		Topics t = new Topics();
		larry = new HashMap<Integer,ArrayList<NeuralObject>>();
		int topicNum = 101;
		reports = v.visitidToFileArrayList();
		//HashMap<Integer,String> topics = n.topicReturn();
		HashMap<Integer,String> topics = t.getTopics();
		while(topicNum < 136)
		{
			larryLines = new ArrayList<NeuralObject>();
			ArrayList<LineObject> visits = getVisits(results , topicNum);
			System.out.println("TOPIC "+topicNum);
			String query = topics.get(topicNum);
			System.out.println("Query : " + query);
			String [] topic = query.split(" ");
			for(int g = 0 ; g < topic.length ; g++)
				System.out.print(topic[g] + " //");
			System.out.println();
			IDrank = new HashMap<String,Integer>();
			std = new HashMap<String,Double>();
			information = new HashMap<String,VisitInfo>();
			keywords = new HashMap<String, ArrayList<Integer>>();
			createTable( topic , visits , reports, topicNum);
			System.out.println("Deviation Sorting" + topicNum);
			printDeviation(topicNum);
			topicNum++;
			if(topicNum == 130)
				topicNum++;
			larryLines.clear();
		}
		System.out.println("CORRECT AFTER DEVIATION " + correctCount2);
		System.out.println("TOTAL AFTER DEVIATION " + totalCount2);
	}
	public static void main(String args[]) throws Exception
	{
		LChart l = new LChart();
	}
	private static ArrayList<LineObject> getVisits(ArrayList<LineObject> res , int topicNum)
	{
		ArrayList<LineObject> temp = new ArrayList<LineObject>();
		for(LineObject l :res)
		{
			if(l.topic == topicNum) //&& l.relevance == 2)
				temp.add(l);
		}
		return temp;
	}
	private static void createTable(String [] top , ArrayList<LineObject > vis ,
			                             HashMap<String,ArrayList<File>> rep, int topicNum) throws FileNotFoundException
	{
		//for(int q = 0; q < top.length;  q++)
			//System.out.print(top[q] + " ,");
		Double [] deviation = new Double[top.length];
		//System.out.println();
		for(LineObject lin : vis)
		{
			Set<String> set = rep.keySet();
			Iterator<String> i = set.iterator();
			String v = lin.visitID;
			while(i.hasNext())
			{
				String s = i.next();
				if(s.equals(v))
				{
					for(int d = 0 ; d < deviation.length ; d++)
						deviation[d] = (double)0;
					//System.out.println("Visit ID :" + s);
					ArrayList< File > theseFiles = rep.get(s);
					int [] [] holder = new int [theseFiles.size()] [top.length];
					int hold = 0;
					for(File f : theseFiles)
					{
						int [] counts = new int [top.length];
						for(int x = 0 ; x < counts.length ; x++)
							counts[x] = 0;
						if(f == null)
							break;
						File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\"+f.getName());
						//System.out.print("Report # " + fo.getName() + "  ");
						Scanner scan = new Scanner(fo);
						while(scan.hasNext())
						{
							String line = scan.nextLine();
							line = line.trim();
							for(int q = 0 ; q < top.length; q++)
								if(line.contains(top[q]))
									counts[q] = 1;
						}
						
						for(int h = 0; h <counts.length; h++)
							holder[hold][h] = counts[h];
						//System.out.println();
						hold++;
					}
					ArrayList<Integer> totalKeywordLine = new ArrayList<Integer>();
					for(int b = 0; b < top.length; b++)
						totalKeywordLine.add(0);
					
					for(int h = 0 ; h < theseFiles.size(); h++)
						for(int w = 0 ; w < top.length ; w++)
							if( holder[h][w] == 1)
								totalKeywordLine.set(w, totalKeywordLine.get(w) + 1 );
					
					for(int h = 0 ; h < theseFiles.size(); h++)
						for(int w = 0 ; w < top.length ; w++)
							deviation[w] = deviation[w] + holder[h][w];
					//System.out.print("Deviation  ");
					//for(int p = 0 ; p < deviation.length ; p++)
						//System.out.print(deviation[p] + " ");
					//System.out.println();
					
					int totalCount = 0;
					for(int h = 0 ; h < theseFiles.size(); h++)
						for(int w = 0 ; w < top.length ; w++)
							if( holder[h][w] == 1)
								totalCount++;
					double average = 0.0;
					//System.out.println("TotalCount " + totalCount + "Top Length " +top.length);
					average = (double)totalCount/ (double)top.length;
					//System.out.println("Before square ");
					//for(int gf = 0 ; gf < deviation.length; gf++)
						//System.out.print(deviation[gf] + " ");
					//System.out.println();
					//DEVIATION IS CORRECT - HOLDS VALUES FOR EACH COLUMN
	                //System.out.println("AVERAGE " + average);
					for(int e = 0 ; e < deviation.length; e++)
					{
						if(deviation[e] == 0){}
						else if(deviation[e] < average)
							deviation[e] = 0.0;
						else
							deviation[e] = (deviation[e] - average) * (deviation[e] - average);
					}
					//System.out.println("After square ");
					//for(int gf = 0 ; gf < deviation.length; gf++)
						//System.out.print(deviation[gf] + " ");
					//System.out.println();
					double totalDeviation = 0.0;
					for(int f = 0 ; f < deviation.length ; f++)
						totalDeviation = totalDeviation + deviation[f];

					//System.out.println("TotalDeviation " +totalDeviation);
					std.put(s, totalDeviation);
					IDrank.put(s, totalCount);
					information.put(s, new VisitInfo(theseFiles.size(), totalCount, top.length,
							average, 3));
					//System.out.println(totalKeywordLine+ " HERE ");
					keywords.put(s,totalKeywordLine);
				}
			}
		}
		printHash(0, topicNum , top);
	}
	private static void printHash(int max, int topicNum, String [] split) throws FileNotFoundException
	{
		Set<String> set = IDrank.keySet();
		Iterator<String> i = set.iterator();
		String topGun = "Topgun";
		int topCount = 0;
		while(i.hasNext())
		{
			String s = i.next();
			Integer it = IDrank.get(s);
			if(it > topCount)
			{
				topCount = it;
				topGun = s;
			}
		}
		if(!(topGun.equals("Topgun")))
		{
			//System.out.print(topGun + " " + IDrank.get(topGun) + "  " +std.get(topGun));
			VisitInfo thisVis = information.get(topGun);
			System.out.print(topGun + " " + thisVis.reports + " " + thisVis.total + " " +thisVis.words);
			System.out.print(" " + thisVis.average);
			ArrayList<LineObject> visits = getVisits(results,topicNum);
			for(LineObject l : visits)
			{
				if(topGun.equals(l.visitID))
				{
					System.out.println("   "+ l.relevance + "   "   +std.get(topGun));
					larryLines.add(new NeuralObject(topGun,IDrank.get(topGun),std.get(topGun),l.relevance));
					if(l.relevance == 2)
						correctCount++;
					totalCount++;
						
				}
			}
			//printMatrix(topGun , visits , split);
			
			IDrank.remove(topGun);
		}
		if(IDrank.isEmpty() || max > 48){
			//Boolean empty = true;
			//ArrayList<Integer> matrixTotals = keywords.get(topGun);
			//System.out.println("Matrix Total " +matrixTotals);
			//for(Integer ii : matrixTotals)
				//if(ii == 0)
				//	empty = false;
			//System.out.println(larryLines);
			//System.out.println("HERE");
			//System.out.println(topicNum);
				//if(empty)
				//{
				//	System.out.println("ENTER");
			larry.put((Integer)topicNum,larryLines);
				//}
		}
		else
			printHash(max + 1 , topicNum , split);
	}
	
	private static void printMatrix(String visitID , ArrayList<LineObject> vis, 
			String [] top) throws FileNotFoundException
    {
			for(int x = 0; x < top.length ; x++)
				System.out.print("     "+top[x]);
			System.out.println();
			String v = visitID;
			ArrayList< File > theseFiles = reports.get(v);
			int [] [] holder = new int [theseFiles.size()] [top.length];
			int hold = 0;
			for(File f : theseFiles)
			{
				int [] counts = new int [top.length];
				for(int x = 0 ; x < counts.length ; x++)
					counts[x] = 0;
				if(f == null)
					break;
				File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\"+f.getName());
				System.out.print("Report # " + fo.getName() + "  ");
				Scanner scan = new Scanner(fo);
				while(scan.hasNext())
				{
					String line = scan.nextLine();
					line = line.trim();
					for(int q = 0 ; q < top.length; q++)
					{	
						if(line.contains(top[q]))
							counts[q] = 1;
					}
				}
				for(int x = 0 ; x < counts.length ; x++)
					System.out.print(counts[x] + "  ");
				for(int h = 0; h <counts.length; h++)
					holder[hold][h] = counts[h];
				System.out.println();
				hold++;
	
			}
			
    }//end method
	public static void printDeviation(int topic)
	{
		ArrayList<NeuralObject> temp = larry.get(topic);
		//System.out.println(larry.get(topic));
		NeuralObject x = temp.get(0);
		int holder = 0;
		for(int n = 0; n < temp.size(); n++)
		{
			if(x.deviation > temp.get(n).deviation)
			{
				x = temp.get(n);
				holder = n;
			}
		}
		System.out.println(x.visit + "   " +x.wordCount + "  "+x.deviation + "  " + x.relevant); 
		if(x.relevant == 2)
			correctCount2++;
		totalCount2++;
		temp.remove(holder);
		if(temp.isEmpty() || temp.size() < 41){}//larry.size()-10){}
		else
			printDeviation(topic);
	}

}//end class
