import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class searchIndex {

	public searchIndex(HashMap<Integer,String> topics) throws ParseException, CorruptIndexException, LockObtainFailedException, IOException
	{
		String [] words = getStopWords();
		Set<Object> stopWords = StopFilter.makeStopSet(Version.LUCENE_35,words,true);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35,stopWords);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LuceneExpansionSienaCollege.txt")));
		String userName = System.getProperty("user.name");
		Directory dir = new SimpleFSDirectory(new File("C:\\Users\\"+userName+"\\Desktop\\Index"));
		Set<Integer> set = topics.keySet();
		Iterator<Integer> i = set.iterator();
		double numTwos = 0;
		double totalCount = 0;
		while(i.hasNext()){
			double thisTwos = 0;
			double thisTotalTwos = 0;
			Integer num = i.next();
			String temp = topics.get(num);
			String querystr = temp;
			//String expansion = tester();
			Query q = new QueryParser(Version.LUCENE_35,"title",analyzer).parse(temp);
			int hitsPerPage = 1500;
			IndexReader reader = IndexReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			//System.out.println(num+" TOPIC : " + temp);
			//System.out.println("Found " + hits.length + " hits.");
			ArrayList<String> visits = new ArrayList<String>();
			ArrayList<String> noRepeats = new ArrayList<String>();
			ArrayList<LineObject> results = Results.results();
			int topCount = 0;
			for(int x=0; topCount < 10 && x < hits.length ;++x) 
			{
				int docId = hits[x].doc;
				Document d = searcher.doc(docId);
				String visitId = d.get("visitID");
				if(visitId.equals("NULL")){

				}
				else{
					if(!noRepeats.contains(visitId)){
						noRepeats.add(visitId);
					topCount++;
					out.write(num + " Q0 " + d.get("visitID") + "\t");
					out.write(topCount + "  "+hits[x].score +" Siena2");
					out.write("\n");
					System.out.print(num + "  Q0" + d.get("visitID") + "\t");
					System.out.print(topCount + "  "+hits[x].score +" Siena2");
					System.out.println();
					}
				}
			}
			noRepeats.clear();
			//System.out.println("Relevant Documents :" +thisTwos);
			//System.out.println("Total Documents    :" +thisTotalTwos);
			searcher.close();
		}
		System.out.println("Total Relevant" +numTwos);
		System.out.println("Total Counter  "+totalCount);

		out.flush();
		out.close();

	}
	public String tester() throws IOException
	{
		ArrayList<String> temp = new ArrayList<String>();
		File f = new File("src/larrywrds.txt");
		Scanner sc = new Scanner(f);
		while(sc.hasNext())
			temp.add(sc.nextLine());
		String topic = "Patients with complicated GERD who receive endoscopy";
		for(String s : temp)
			topic = topic + " "+s;
		return topic;
	}
	public String[] getStopWords()
	{

		String [] stops = { "a", "an", "and", "are", "as", "at", "be", "but", "by",
				"for", "if", "in", "into", "is", "it",
				"no", "not", "of", "on", "or", "such",
				"that", "the", "their", "then", "there", "these",
				"they", "this", "to", "was", "will", "with","were",
				"patients", "treated","who","had","admitted","discharged","during",
				"assessed","received","help"
		};
		return stops;
	}
}
