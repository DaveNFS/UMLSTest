import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import UtsMetathesaurusContent.UtsFault_Exception;

public class QueryProcessor2 {
	UMLSAccess ua = new UMLSAccess();
	private HashMap<Integer,String> topics = new HashMap<Integer,String>();
	private String description = " ";
	private int key = 0; 

	String[] stops = {"a", "an", "and", "are", "as", "at", "be", "but", "by",
	"for", "if", "in", "into", "is", "it",
	"no", "not", "of", "on", "or", "such",
	"that", "the", "their", "then", "there", "these",
	"they", "this", "to", "was", "will", "with","were","which","while",
	"patients", "treated","who","had","admitted","discharged","during",
	"assessed","received","help","being","history","diagnosed","total","treatment",
	"hospitalized","localized","from"};	

	String[] extraWords ={"adult","adults","child","diagnosis","male","female","therapy","emergency", "room",
	"complicated","specified","chronic","infection","children","underwent","procedure","secondary","receive","received",
	"present","woman","women","man","men","surgery","home","hospital","admission","minimally","minimum","presented","products",
	"emergency" , "department" , "room"};
	
	public String[] punc = {",","[","]",".",",","(",")","\\","/","*","^",":"};
	public QueryProcessor2() throws Exception{
		try 
		{
			Scanner sc = new Scanner(new File("src/topics136-185.txt"));
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.contains("<num>")){
					String[] results = line.split(" ");
					key = Integer.parseInt(results[2]);
				}
				else if(line.contains("<desc>")){
					ArrayList<String> topicString = new ArrayList<String>();					
					StanfordPOS tagger = new StanfordPOS();
					description = sc.nextLine().toLowerCase();
					System.out.println("Original Description : " +description);
					String tagged = tagger.tagString(description);
					for(int x = 0; x < punc.length; x++)
						description = description.replace(punc[x],"");
					description = description.trim();
					String desc[] = description.split(" ");
					for(String s:desc)
						topicString.add(s);
					topicString = cleanTopic(topicString);
					description = toTopic(topicString);
					String[] results = tagged.split(" ");
					ArrayList<String> nounPhraseQuery = new ArrayList<String>();
					ArrayList<String> adjNoun = new ArrayList<String>();
					boolean nounPhrase = false;
					String toAdd = "";
					for(int x = 0; x < results.length; x++){
						String s = results[x];
						if(s.contains("_NN") || s.contains("_NNS")){
							s = s.replace("_NNS","");
							s = s.replace("_NN", "");
							toAdd = toAdd + s + " ";
							if(toAdd.length() == s.length() + 1)
								nounPhrase = true;
							if(x == results.length-1){
								toAdd = toAdd.trim();
								if(nounPhrase == true)
									nounPhraseQuery.add(toAdd);
								else
									adjNoun.add(toAdd);
							}
						}
						else if(s.contains("_JJ")||s.contains("_JJR")||s.contains("_JJS")){
							if(nounPhrase == true){
								toAdd = toAdd.trim();
								nounPhraseQuery.add(toAdd);
								s = s.trim();
								s = s.replace("_JJS", "");
								s = s.replace("_JJR", "");
								s = s.replace("_JJ", "");
								toAdd = s + " ";
								nounPhrase = false;
							}
							else{
								s = s.trim();
								s = s.replace("_JJS", "");
								s = s.replace("_JJR", "");
								s = s.replace("_JJ", "");
								toAdd = toAdd + s + " ";
							}
						}
						else if(s.contains("_VBN")||s.contains("_VB")||s.contains("_FW")){
							if(!(toAdd == "")){							
								toAdd = toAdd.trim();
								if(nounPhrase == true)
									nounPhraseQuery.add(toAdd);
								else
									adjNoun.add(toAdd);
								nounPhrase = false;
								toAdd = "";
							}
							if(s.contains("_FW")){
								String temp = s.replace("_FW","");
								temp = temp.trim();
								nounPhraseQuery.add(temp);
							}
						}
						else if(s.contains("_IN")||s.contains("_CC")){
							if(!(toAdd == "")){
								toAdd = toAdd.trim();
								if(nounPhrase == true)
									nounPhraseQuery.add(toAdd);
								else
									adjNoun.add(toAdd);
								nounPhrase = false;
								toAdd = "";
							}
						}
					}
					for(int x=0;x<stops.length;x++){
						if(nounPhraseQuery.contains(stops[x]))
							nounPhraseQuery.remove(stops[x]);
						if(adjNoun.contains(stops[x]))
							adjNoun.remove(stops[x]);
					}
					for(int x=0;x<extraWords.length;x++){
						if(nounPhraseQuery.contains(extraWords[x]))
							nounPhraseQuery.remove(extraWords[x]);
						if(adjNoun.contains(extraWords[x]))
							adjNoun.remove(extraWords[x]);
					}
					//System.out.println("Noun Phrases: " + nounPhraseQuery);
					//System.out.println("Adjective + Noun: "  + adjNoun);
					//System.out.println("Topic : " + description);
					for(int a = 0 ; a <nounPhraseQuery.size(); a++)
						description = description + " " +nounPhraseQuery.get(a);
					for(int a = 0 ; a <adjNoun.size(); a++)
						description = description + " " +adjNoun.get(a);
					
					topics.put(key,description);
				}
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.print("No File Found");
		}
	}
	public HashMap<Integer,String> getTopics()
	{
		return topics;
	}
	public ArrayList<String> cleanTopic(ArrayList<String> topicArray){
		for(int x = 0; x < stops.length; x++){
			if(topicArray.contains(stops[x])){
				topicArray.remove(stops[x]);
				x--;
			}
		}
		return topicArray;
	}
	public ArrayList<String> preQuery(ArrayList<String> topicArray){
		for(int x = 0; x < extraWords.length; x++){
			if(topicArray.contains(extraWords[x])){
				topicArray.remove(extraWords[x]);
				x--;
			}
		}
		return topicArray;
	}
	public String toTopic(ArrayList<String> topicConvert){
		String arrayToString = "";
		for(int x = 0; x < topicConvert.size(); x++){
		{
			if(topicConvert.get(x) != null)
				arrayToString = arrayToString + topicConvert.get(x) + " ";
		}
		}
		return arrayToString;
	}
	public ArrayList<String> arrayToArrayList(String[] arrayConvert){
		ArrayList<String> finalList = new ArrayList<String>();
		for(int x=0; x < arrayConvert.length;x++)
			finalList.add(arrayConvert[x]);
		return finalList;
	}
	
	public ArrayList<String> stringToArrayList(String s){
		ArrayList<String> temp = new ArrayList<String>();
		String results[] = s.split(" ");
		for(String x : results){
			temp.add(x);
		}
		return temp;
	}
	
	public ArrayList<String> clean(ArrayList<String> toClean){
		for(int x=0; x<toClean.size();x++){
			if(toClean.get(x)==null){
				toClean.remove(x);
				x--;
			}
		}
		toClean.trimToSize();
		return toClean;
	}
	public static void main(String[] args) throws Exception{
		QueryProcessor2 p = new QueryProcessor2();
	}
}