import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * Write a description of class IndexCreater here.
 * 
 * @author (Christopher Rivadeneira/Andrew Reynolds) 
 * @version (a version number or a date)
 */
public class IndexCreator
{
    public IndexCreator(HashMap<String,String> chkSumToVisit, HashMap<String,String> icdData) 
    				throws ParseException, CorruptIndexException, LockObtainFailedException, IOException
    {
    	HashMap<String,String> visitFinder = chkSumToVisit;
    	HashMap<String,String> icdDatabase = icdData;
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
        String userName = System.getProperty("user.name");
        Directory dir = new SimpleFSDirectory(new File("C:\\Users\\"+userName+"\\Desktop\\Index"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        String wholeFile = "";
        try
        {
    		String path = "C:\\users\\"+ userName +"\\MedicalFiles";
    		File workingDirectory = new File(path);
    		File[] dirListing = workingDirectory.listFiles();
    		for(int x = 0; x < dirListing.length; x++)
    		{
    			Scanner sc = new Scanner(dirListing[x]);
    			Document doc = new Document();
    			while(sc.hasNextLine())
            		{	
    					String temp = sc.nextLine();
    					if(temp.contains("checksum")){
    						String docInfo = temp;
    						docInfo = docInfo.replace("<checksum>","");
    						docInfo = docInfo.replace("</checksum>","");
    						String toBeAdded = "File Name: " + dirListing[x] + "\n" + "Checksum: " + docInfo
    								+ "\n" + "Visit ID: " + visitFinder.get(docInfo) + "\n";
    						String visitId = visitFinder.get(docInfo);
    						doc.add(new Field("Identify", toBeAdded, Field.Store.YES, Field.Index.ANALYZED));
    						doc.add(new Field("visitID",visitId,Field.Store.YES,Field.Index.ANALYZED));
    						wholeFile = wholeFile + toBeAdded + "\n";
    					}
    					
    					else if(temp.contains("<admit_diagnosis>")){
    						wholeFile = wholeFile + "Admit Diagnosis\n";
    						String icdReplacements = "";
    						String toSplit = temp.replace("<admit_diagnosis>","");
    						toSplit = toSplit.replace("</admit_diagnosis>", "");
    						String results[] = toSplit.split(",");
    						for(int y = 0; y < results.length; y++){
    							String checkCode = results[y].replace(".","");
    							if(icdDatabase.containsKey(checkCode)){
    								icdReplacements = "ICD Code: " + results[y] + "\n" + "Description: "
    										+ icdDatabase.get(checkCode) + "\n";
    							}
    						wholeFile = wholeFile + icdReplacements + "\n";
    						}
    					}
    					else if(temp.contains("<discharge_diagnosis>")){
    						wholeFile = wholeFile + "Discharge Diagnosis\n";
    						String icdReplacements = "";
    						String toSplit = temp.replace("<discharge_diagnosis>", ",");
    						toSplit = toSplit.replace("</discharge_diagnosis>", ",");
    						String results[] = toSplit.split(",");
    						for(int y = 1; y < results.length; y++){
    							String checkCode = results[y].replace(".","");
    							if(icdDatabase.containsKey(checkCode)){
    								icdReplacements = "ICD Code: " + results[y] + "\n" + "Description: "
    										+ icdDatabase.get(checkCode) + "\n";
    								}
    						wholeFile = wholeFile + icdReplacements + "\n";	
    						}
    					}
    					else if(temp.contains("<report_text>")){
    						String fullReport = "";
    						while(sc.hasNextLine()){
    							String toAdd = sc.nextLine();
    							//toAdd = editLine(toAdd);
    							if(toAdd.contains("</report_text>"))
    								break;
    							fullReport = fullReport + toAdd + "\n";
    						}
    						wholeFile = wholeFile + fullReport + "\n";
    					}
    					wholeFile = wholeFile + temp;	
            		}
				doc.add(new Field("title", wholeFile, Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(doc);
				sc.close();
				wholeFile = "";
    		}
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Caught, File Not Found Exception");
            throw e;
        }
        writer.close();
    }
    /*
     * Takes in a line of code from report file...checks to see if it has a common phrase.
     * If it does, takes out common phrase by returing substring. If not, returns line 
     * as is.
     */
    public String editLine(String line) throws FileNotFoundException
    {
    	String temp = removeFormText(line);
    	//temp = removeNegation(temp);
    	return temp;
    }
    public String removeFormText(String line) throws FileNotFoundException
    {
    	File f = new File("src/FormText.txt");
    	Scanner scan = new Scanner(f);
    	ArrayList<String> phrases = new ArrayList<String>();
    	while(scan.hasNext())
    	{
    		String str = scan.nextLine();
    		phrases.add(str);
    	}
    	for(String s : phrases)
    	{
    		if(line.contains(s))
    			return line.replace(s, "");
    	}
    	return line;
    	
    }
    public String removeNegation(String line)
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