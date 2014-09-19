import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class reportOrganizer {
	private Scanner sc;
	private HashMap<String, Integer> commonHeaders = new HashMap<String, Integer>();
	
	public reportOrganizer(){
		File directory = new File("C:\\Users\\Chris\\Desktop\\trec_2012data_20120524");
		File[] dirListing = directory.listFiles();
		boolean search = false;
		
 		for(int x = 0; x < dirListing.length; x++){
			try{
				sc = new Scanner(dirListing[x]);
				while(sc.hasNext()){
					String temp = sc.next();
					if(search == false){
						if(temp.contains("<type>")){
							if(temp.contains("RAD")){
								search = true;
							}
						}
					}
						if(search == true){
							if(temp.length() <= 25 && isUpperCase(temp) == true && noNumbers(temp) == true && temp.contains(":")) {
								if(commonHeaders.containsKey(temp)){
									updateCount(temp);
								}
								else{
									int test = 1;
									commonHeaders.put(temp,test);
								}
							}
						}
					}
				}
			
			catch(FileNotFoundException e){
				System.err.print("File Not Found");
			}
			search = false;
		}
		Set<String> s = commonHeaders.keySet();
		Iterator<String> i =  s.iterator();
		while(i.hasNext()){
			String header = i.next();
			System.out.println(header + ": " + commonHeaders.get(header));
		}
	}
	public void updateCount(String editKey){
		int temp = commonHeaders.get(editKey);
		commonHeaders.put(editKey,(temp+1));
	   }
	
	public boolean isUpperCase(String s)
	{
	        for (int i=0; i<s.length(); i++)
	        {
	                if (Character.isLowerCase(s.charAt(i)))
	                {
	                        return false;
	                }
	        }
	        return true;
	}
	public boolean noNumbers(String s){
		for(int x = 0; x < 10; x++){
			if(s.contains(Integer.toString(x))){
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args){
		reportOrganizer rO = new reportOrganizer();
	}
}
