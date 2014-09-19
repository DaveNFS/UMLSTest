import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import UtsMetathesaurusContent.UtsFault_Exception;

public class QueryProcessor {
	
	private HashMap<Integer,String> topics = new HashMap<Integer,String>();
	private int key = 0;
	private String description="";
	
	public QueryProcessor() throws UtsFault_Exception, UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception
	{
		UMLSAccess ua = new UMLSAccess();
		try {
			Scanner sc = new Scanner(new File("src/topics101-135.txt"));
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.contains("<num>"))
				{
					StringTokenizer st = new StringTokenizer(line);
					for(int x = 0; x < st.countTokens(); x++)
					{
						st.nextToken();
					}
					key = Integer.parseInt(st.nextToken());
				}
				if(line.contains("<desc>")){
				   	String [] stops = { "a", "an", "and", "are", "as", "at", "be", "but", "by",
				    		"for", "if", "in", "into", "is", "it",
				    		"no", "not", "of", "on", "or", "such",
				    		"that", "the", "their", "then", "there", "these",
				    		"they", "this", "to", "was", "will", "with","were",
				    		"patients", "treated","who","had","admitted","discharged","during",
				    		"assessed","received","help","being","history","diagnosed","total","treatment",
				    		"hospitalized","localized","from"
				    		};
					description = sc.nextLine();
					description = description.toLowerCase();
					String[] results = description.split(" ");
					for(int x = 0; x < stops.length; x++){
						for(int y = 0; y < results.length; y++){
							if(stops[x].equalsIgnoreCase(results[y])){
								results[y] = "";
							}
						}
					}
					description = "";
					for(String s : results){
						if(!s.equals(""))
						description = description + s + " ";
					}	
					
					
					String[] extraWords = {"adult","adults","child","diagnosis","male","female","therapy",
							"complicated","specified","chronic","infection","children","underwent",
							"present","woman","women","man","men","surgery","home"};
					String[] preQuery = description.split(" ");
					for(int x = 0; x < extraWords.length; x++){
						for(int y = 0; y < preQuery.length; y++){
							if(extraWords[x].equalsIgnoreCase(preQuery[y])){
								preQuery[y] = "";
							}
						}
					}
					String temp = "";
					for(String s : preQuery){
						if(!s.equals(""))
						temp = temp + s + " ";
					}	 
					preQuery = temp.split(" ");
					
					ArrayList<String> finalResults = new ArrayList<String>();
					if(preQuery.length > 2){
						for(int x = 0; x < preQuery.length; x++){
							String[] UMLSresults = ua.searchUMLS(preQuery[x]);
							for(int y = 0; y < UMLSresults.length; y++){
								if(!finalResults.contains(UMLSresults[y])){
									//System.out.println(UMLSresults[y]);
									finalResults.add(UMLSresults[y]);
								}
							}
						}
					}
					else{
						String search;
						if(preQuery.length == 2){
							search = preQuery.toString();
						}
						search = preQuery[0];
						String[] UMLSresults = ua.searchUMLS(search);
						for(int y = 0; y < UMLSresults.length; y++){
							if(!finalResults.contains(UMLSresults[y]))
								finalResults.add(UMLSresults[y]);
						}
					}
					for(String s : finalResults){
						description = description + s + " ";
					}
					ArrayList<String> finalDescription = new ArrayList<String>();
					description = description.replace("[", "");
					description = description.replace("]", "");
					String[] toCut = description.split(" ");
					for(int x = 0; x < toCut.length; x++){
							finalDescription.add(toCut[x]);
					}
					description = (finalDescription.toString());
					System.out.println("Topic " + key + " : " + description);
				topics.put(key,description);
				}
			}
		}
		catch(FileNotFoundException e){	
			System.err.print("No File Found");
		}
	}	
	public HashMap<Integer,String> getTopics()
	{
		return topics;
	}
	public static void main(String[] args) throws UtsFault_Exception, UtsMetathesaurusFinder.UtsFault_Exception, UtsSecurity.UtsFault_Exception{
		QueryProcessor p = new QueryProcessor();
	}

}

	
