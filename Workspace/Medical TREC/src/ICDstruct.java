import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class ICDstruct {
	
	private HashMap<String,String> icdDatabase = new HashMap<String,String>();
	private ArrayList<String> codes = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	
	public ICDstruct(){
		createDatabase();
		returnDatabase();
	}
	
	public void createDatabase(){
		int count = 0;
		String disease = "";
		String code = "";
		
		try {
			Scanner sc = new Scanner(new File("src/ICD_CODES.txt"));
			while(sc.hasNextLine()){
				codes.add(sc.nextLine());
			}
			sc = new Scanner(new File("src/ICD_DESCRIPTIONS.txt"));
			while(sc.hasNextLine()){
				descriptions.add(sc.nextLine());
			}
		}
		catch(FileNotFoundException e){
			System.err.print("File Not Found");
		}
		if(codes.size() == descriptions.size()){
			for(int x = 0; x < codes.size(); x++){
				icdDatabase.put(codes.get(x),descriptions.get(x));
			}
		}
	}
	
	public HashMap<String,String> returnDatabase(){
		return icdDatabase;
	}
	
	public void printDatabase(){
		Set<String> s = icdDatabase.keySet();
		Iterator<String> i = s.iterator();
		while(i.hasNext()){
			String key = i.next();
			System.out.println("Code: " + key + "\n" + "Description: " +
					icdDatabase.get(key) + "\n");
		}
	}
}
