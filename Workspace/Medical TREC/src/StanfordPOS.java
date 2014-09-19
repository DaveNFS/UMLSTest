import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordPOS {
	
	String userName = System.getProperty("user.name");
	private String pathPOS = "C:\\Users\\"+userName+"\\Documents\\GitHub\\siena-trec-medical\\Workspace\\Medical TREC\\src\\bidirectional-distsim-wsj-0-18.tagger";

	MaxentTagger tagger;
	
	public StanfordPOS() throws Exception{
		tagger = new MaxentTagger(pathPOS);
	}
	
	public String tagString(String input){
		
		String taggedString = tagger.tagString(input);
		
		return taggedString;
		
	}
	
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    @SuppressWarnings("unchecked")
    List<Sentence<? extends HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[1])));
    for (Sentence<? extends HasWord> sentence : sentences) {
      Sentence<TaggedWord> tSentence = MaxentTagger.tagSentence(sentence);
      System.out.println(tSentence.toString(false));
    }
  }

}
