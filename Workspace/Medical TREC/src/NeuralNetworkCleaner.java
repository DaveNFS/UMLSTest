import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class NeuralNetworkCleaner {
	HashMap<Integer,String> topics = new HashMap<Integer,String>();
	int topicNum = 101;
	String[] stops = {"a", "an", "and", "are", "as", "at", "be", "but", "by",
	"for", "if","into", "is", "it", "of",
	"no", "not", "on", "or", "such",
	"that", "the", "their", "then", "there", "these",
	"they", "this", "to", "was", "will", "with","were","which","while",
	"patients", "treated","who","had","admitted","discharged","during",
	"assessed","received","help","being","history","diagnosed","total","treatment",
	"hospitalized","localized","from","episodes"};	

	String[] extraWords ={"adult","adults","child","diagnosis","male","female","therapy","emergency", "room",
	"complicated","specified","chronic","infection","children","underwent","procedure","secondary","receive","received",
	"present","woman","women","man","men","surgery","home","hospital","admission","minimally","minimum","presented","products"};
	
	String[] remove = {"adult","adults","child","children","male","female","patient","patients","man", "of","products",
			//"care", "complicated","acute", "dependant", "localized", "secondary", "temporary", "admissions", "medications", "treatment", "procedure",
			"men","woman","women","hospital","emergency","room",",","[","]",".",",","(",")","\\","/","*","^",":"};
	
	public NeuralNetworkCleaner() throws Exception {
		StanfordPOS tagger = new StanfordPOS();
		Scanner sc = new Scanner(new File("src/topics101-135.txt"));
		//Scanner sc = new Scanner(new File("src/testTopic.txt"));
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("<desc>")){
				ArrayList<String> nounPhraseQuery = new ArrayList<String>();
				ArrayList<String> adjNoun = new ArrayList<String>();
				boolean nounPhrase = false;
				boolean deleteFlag = false;
				String toAdd = "";
				String origTopic = sc.nextLine();
				origTopic = origTopic.trim();
				origTopic = origTopic.toLowerCase();
				String[] divide = origTopic.split(" ");
				ArrayList<String> topicArray = new ArrayList<String>();
				for(int x = 0; x < divide.length; x++){
					topicArray.add(divide[x]);
				}
				for(int x = 0; x < remove.length; x++){
					if(topicArray.contains(remove[x])){
						topicArray.remove(remove[x]);
						x--;
					}
				}
				//ArrayList<String> parts = getBodyParts();
				//for(int y = 0; y <parts.size(); y++)
				//{
					//if(topicArray.contains(parts.get(y))){
						//topicArray.remove(parts.get(y));
						//y--;
					//}
				//}
				origTopic = toTopic(topicArray);
				origTopic = origTopic.trim();
				origTopic = origTopic.toLowerCase();
				String tagged = tagger.tagString(origTopic);
				String[] results = tagged.split(" ");
				boolean wordChecker = false;
				for(int x = 0; x < results.length; x++){
					String s = results[x];
					if(s.contains("-LRB-")){
						toAdd = toAdd.trim();
						if(nounPhrase == true){
							nounPhraseQuery.add(toAdd);
							nounPhrase = false;
						}
						else
							adjNoun.add(toAdd);
						toAdd = "";	
						deleteFlag = true;
					}
					if(deleteFlag == true){
						if(s.contains("-RRB-"))
							deleteFlag = false;
					}
					else{
						if(toAdd.equals("")){
							if(s.contains("_NNS")||s.contains("_NN")){
								nounPhrase = true;
								s = removeTag(s);
								toAdd = toAdd + s + " ";							
							}
							else if(s.contains("_JJ")||s.contains("_JJR")||s.contains("_JJS")){
								s = removeTag(s);
								toAdd = toAdd + s + " ";
							}
						}
						else{
							if(s.contains("_NNS")||s.contains("_NN")||s.contains("_RB")){
								s = removeTag(s);
								toAdd = toAdd + s + " ";
								if(wordChecker == true){
									wordChecker = false;
								}
							}
							if(s.contains("_JJ")||s.contains("_JJR")||s.contains("_JJS")){
								if(nounPhrase == true && wordChecker == false){
									toAdd = toAdd.trim();
									toAdd = toAdd.replace("_IN","");
									toAdd = toAdd.replace("_DT","");
									toAdd = toAdd.trim();
									nounPhraseQuery.add(toAdd);
								}
								if(wordChecker == false){
									toAdd = "";
									s = removeTag(s);
									toAdd = toAdd + s + " ";
								}
								else{
									s = removeTag(s);
									toAdd = toAdd + s + " ";
								}
							}
							
							if(s.contains("_DT")){
								wordChecker = true;
								toAdd = toAdd + s + " ";
							}
							if(toAdd.contains("_DT") && wordChecker == true){
							}
							else if(toAdd.contains("of_IN") || toAdd.contains("the_DT")){
								toAdd = toAdd.trim();
								toAdd = toAdd.replace("_IN","");
								toAdd = toAdd.replace("_DT","");
								if(nounPhrase == true){
									nounPhraseQuery.add(toAdd);
									nounPhrase = false;
								}
								else
									adjNoun.add(toAdd);
								toAdd = "";	
							}
							else if(s.contains("of_IN")){
								s = removeTag(s);
								toAdd = toAdd + s + " ";
							}
							else if(s.contains("_CC")||s.contains("_IN")||s.contains("_WP")
									||s.contains("_VBN")||s.contains("_VB")||s.contains("_FW")){
								toAdd = toAdd.trim();
								if(nounPhrase == true){
									nounPhraseQuery.add(toAdd);
									nounPhrase = false;
								}
								else
									adjNoun.add(toAdd);
								toAdd = "";
							}
						}
					}
				}
				if(toAdd.equals("")){}
				else{
					toAdd = toAdd.trim();
					if(nounPhrase == true)
						nounPhraseQuery.add(toAdd);
					else
						adjNoun.add(toAdd);
				}
				//System.out.println(topicNum + ":");
				//System.out.print(nounPhraseQuery);
				//System.out.print(",");
				//System.out.println(adjNoun);
				String topic = toTopic(nounPhraseQuery);
				topic = topic + "" + (toTopic(adjNoun));
				topics.put(topicNum, topic);
				topicNum++;
			}
			
			
		}
	}
	public String removeTag(String stringWithTag){
		String returnString = stringWithTag;
		returnString = returnString.replace("_NNS", "");
		returnString = returnString.replace("_NN", "");
		returnString = returnString.replace("_JJ", "");
		returnString = returnString.replace("_JJR", "");
		returnString = returnString.replace("_JJS", "");
		returnString = returnString.replace("_IN", "");
		returnString = returnString.replace("_CC", "");
		returnString = returnString.replace("_DT", "");
		returnString = returnString.replace("_RB","");
		returnString = returnString.trim();
		
		return returnString;
	}
	public static void main(String[] args) throws Exception{
		NeuralNetworkCleaner nnc = new NeuralNetworkCleaner();
		HashMap<Integer,String> x = nnc.topicReturn();
		int topic = 101;
		//while(topic < 136)
		//{
			//System.out.println("Topic:"+topic + " = "+x.get(topic));
			//topic++;
		//}
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
	public HashMap<Integer,String> topicReturn(){
		return topics;
	}
	public static ArrayList<String> getBodyParts() throws IOException
	{
		File f = new File("src/bodyparts.txt");
		Scanner scan = new Scanner(f);
		ArrayList<String> temp = new ArrayList<String> ();
		while(scan.hasNext())
		{
			String line = scan.nextLine();
			line = line.toLowerCase();
			temp.add(line);
		}
		return temp;
	}
}
