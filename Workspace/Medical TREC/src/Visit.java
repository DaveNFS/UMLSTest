import java.util.*;
import java.io.*;
import java.util.HashMap;


public class Visit 
{
	private ArrayList <File> allFiles;
	//Hashmap with checksums, files
	private HashMap<String,File> fileHash;
	//Hashmap with visit ids, related checksums
	private HashMap<String, ArrayList<String>> visitMappings = new HashMap<String, ArrayList<String>>();
	private HashMap<String, ArrayList<String>> visitMap = new HashMap<String, ArrayList<String>>();
	//Hashmap with visit ids, related files - one entry in one_visit is equal to one visit
	private HashMap<String,ArrayList<File>> one_Visit = new HashMap<String,ArrayList<File>>();
	//Hashmap with visit ids, related Reports...report(file) passes in a file 
	private HashMap<String,ArrayList<report>> allReports = new HashMap<String,ArrayList<report>>();
	public Visit() throws FileNotFoundException
	{
		createHashMap();
		mapping();
		visits();	
		converter();
		//Set<String> set = one_Visit.keySet();
		//Iterator<String> i = set.iterator();
		//while(i.hasNext())
		//{
			//String visit = i.next();
			//ArrayList<File> xx = (ArrayList<File>)one_Visit.get(visit);
			//for(int f = 0; f < xx.size(); f++)
			//{
				//if(xx.get(f) != null)
					//if(xx.get(f).getName().equals("report31485.xml"))
						//System.out.println(visit);
			//}
		//}
		
	}
	public void visits()
	{
		Set<String> set = visitMappings.keySet();
		Iterator <String> i = set.iterator();
		while(i.hasNext()){
		    String idNum = i.next();
			ArrayList<String> checksums = (ArrayList<String>)visitMappings.get(idNum);
			ArrayList<File> files = new ArrayList<File>();
			for(String cS : checksums) // searching through checksums matched up to 1 visit ID			
				files.add(fileHash.get(cS)); //adds files that are matched up to checkSum into files			
			one_Visit.put(idNum,files);		
		}
	}
	/*
	 * Return a hashmap that contains visitId and arraylist of all
	 * the reports in a single visit. An entry represents one visit
	 */
	public HashMap<String,ArrayList<report>> getVisits()
	{
		return allReports;
	}
	public HashMap<String,ArrayList<String>> visitidToChecksumArrayList()
	{
		return visitMap;
	}
	public HashMap<String,ArrayList<File>> visitidToFileArrayList()
	{
		return one_Visit;
	}
	public HashMap<String,File> checksumToFile()
	{
		return fileHash;
	}
	public ArrayList<File> getAllFiles()
	{
		return allFiles;
	}
	/*
	 * Converts the one_Visit hashmap, which is String,ArrayList<File> to a new
	 * hashmap that which is called allReports that has String,ArrayList<Report.
	 * Passes file into report
	 */
	private void converter() throws FileNotFoundException
	{
		Set<String> set = one_Visit.keySet();
		Iterator<String> i = set.iterator();
		while(i.hasNext())
		{
			String id = i.next();
			ArrayList<File> files = new ArrayList<File>();
			ArrayList<report> reports = new ArrayList<report>();
			for(File f : files)
			{
				report r = new report(f);
				reports.add(r);
			}
			allReports.put(id,reports);
		}
	}
	/*
	 * Search through a folder...creates arraylist of Files in allFiles from Files
	 * in MedicalFiles folder. Creates hashmap with <String,File> of checksum 
	 * and file. 
	 */
	public void createHashMap() throws FileNotFoundException
	{
		fileHash = new HashMap<String,File>();
		String system = System.getProperty("user.name");
		String path = "C:\\users\\"+ system +"\\MedicalFiles";
		allFiles = new ArrayList<File> ();
        createFileArrayList(new File(path));
		for(File f : allFiles)
		{
			File fo = new File("C:\\users\\"+system+ "\\MedicalFiles\\"+f.getName());
			Scanner scan = new Scanner(fo);
			String one = scan.nextLine();
			String two = scan.nextLine();
			String three = scan.nextLine();
			//Creates Substring between <checksum> and </checksum>
			String t = three.substring(10,three.length()-11);
			fileHash.put(t, f);
		}
	}
	/*
	 * Creates ArrayList with all the files from Medical Trec folder in it
	 */
	public void createFileArrayList(File fo) throws FileNotFoundException
    {
 		    System.out.println("Searching in..." + fo.getName());
            String internalNames[] = fo.list();
            System.out.println(internalNames.length);
            for(int i=0; i<internalNames.length; i++)
            	allFiles.add(new File(fo.getName() + "\\" + internalNames[i]));
    }
	/*
	 * Creates a hashmap between visitID and arraylist of checksums called visitMapping
	 */
	public HashMap<String, ArrayList<String>> mapping()
	{
		String reportID = "";
		String key = "";
		int count = 0;;
		try {
			Scanner sc = new Scanner(new File("src/UnivOfPittReportMappingToVisit.txt"));
			if(sc.hasNextLine()){
				sc.nextLine();
			}
			while(sc.hasNext()){
				count++;
				if((count % 3) == 1)
					reportID = sc.next();
				else if((count % 3) == 0){
					key = sc.next();
					if(visitMappings.containsKey(key)){
					    updateEntry(key, reportID);
					   }
					else{
					    ArrayList<String> newList = new ArrayList<String>();
					    newList.add(reportID);
					    visitMappings.put(key, newList);
					   }
				}
				else
					sc.next();
			}
			return visitMappings;
		}
		catch(FileNotFoundException e){	
			System.err.print("No File Found");
			return null;
		}
	}
	public void updateEntry(String editKey,String addEntry){
	    ArrayList<String> update = visitMappings.get(editKey);
	    update.add(addEntry);
	    visitMappings.put(editKey,update);
	   }
	public void prints()
	{
		Set<String> sets = one_Visit.keySet();
		Iterator<String> ii = sets.iterator();
		while(ii.hasNext()){
			System.out.println("ENTER");
		    String idNum = ii.next();
		    System.out.println("Key: " + idNum);
			System.out.println(one_Visit.get(idNum));
		}
		
	}
	/*
	 * Return a hashmap<String,String> that holds checksums : visit id
	 */
	public HashMap<String,String> checkToVisitHash() throws FileNotFoundException
	{
		HashMap<String,String> map = new HashMap<String,String>();
		Set<String> set = visitMappings.keySet();
		Iterator<String> i = set.iterator();
		while(i.hasNext())
		{
			String visits = i.next();
			ArrayList<String> checksums = (ArrayList<String>)visitMappings.get(visits);
			for(String s : checksums)
				map.put(s, visits);
		}
		return map;
	}	
} // end class
