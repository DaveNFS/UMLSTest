import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

import UtsMetathesaurusContent.UtsFault_Exception;

public class MedicalDriver
{
	public static void main(String args[]) throws Exception {
		QueryProcessorUpdate p = new QueryProcessorUpdate();
		Visit v = new Visit();
		HashMap<String,ArrayList<File>> files = v.visitidToFileArrayList();
		//for(int x = 0; x < files.get("276bKLtste7W").size(); x++){
		//	System.out.println(files.get("276bKLtste7W").get(x));
		//}
		//HashMap<String,ArrayList<String>> test = v.mapping();
		//HashMap<String,ArrayList<File>> idToFiles = v.visitidToFileArrayList();
		//Set<String> s = test.keySet();
		//Iterator<String> i = s.iterator();
		//HashMap<String,String> ch = v.checkToVisitHash();
		//ICDstruct icd = new ICDstruct();
		//IndexCreator ic = new IndexCreator(ch,icd.returnDatabase());
		
		
		HashMap <Integer,String> topics = new HashMap<Integer,String>();
		//System.out.println("\n\nTopics:" + topics.get(129) +"\n\n");
		HashMap <String,ArrayList<String>> mappings = v.visitidToChecksumArrayList();
		topics = p.getTopics();
		searchIndex si = new searchIndex(topics);
	}
} 