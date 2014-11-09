package IR;

import TF_IDF.TfIdf;
import MapReduce.*;

import java.lang.reflect.Method;
import snowball.*;
import java.io.*;
import java.lang.StringBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Set;

// This class is a container for the document set + any functions
// needed to create the set.

public class DocumentSet {

	private int fileCount =0;
    private Map<String, Double> fileLengths = new HashMap<String, Double>();

    private Map<String, Map<String, Integer>> termIndex = new HashMap<String, Map<String, Integer>>();

    private String[] stopWordsArray ={
		"", " ", "a", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against",
		"ain't", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among",
		"amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere",
		"apart", "appear", "appreciate", "appropriate", "are", "aren't", "around", "as", "aside", "ask", "asking", "associated",
		"at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before",
		"beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both",
		"brief", "but", "by", "c'mon", "c's", "came", "can", "can't", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes",
		"clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains",
		"corresponding", "could", "couldn't", "course", "currently", "definitely", "described", "despite", "did", "didn't", "different",
		"do", "does", "doesn't", "doing", "don't", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either",
		"else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything",
		"everywhere", "ex", "exactly", "example", "except", "far", "few", "fifth", "first", "five", "followed", "following", "follows",
		"for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes",
		"going", "gone", "got", "gotten", "greetings", "had", "hadn't", "happens", "hardly", "has", "hasn't", "have", "haven't", "having", "he",
		"he's", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him",
		"himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "i'd", "i'll", "i'm", "i've", "ie", "if", "ignored", "immediate", "in", 
		"inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isn't", "it", "it'd", 
		"it'll", "it's", "its", "itself", "just", "keep", "keeps", "kept", "know", "known", "knows", "last", "lately", "later", "latter",
		"latterly", "least", "less", "lest", "let", "let's", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", 
		"may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", 
		"namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", 
		"none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", 
		"on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", 
		"overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably",
		"probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", 
		"respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem",
		"seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", 
		"shouldn't", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat",
		"somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "t's", "take", "taken", "tell", "tends", "th", 
		"than", "thank", "thanks", "thanx", "that", "that's", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "there's", 
		"thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "they'd", "they'll", "they're", "they've", "think", "third", 
		"this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", 
		"towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", 
		"upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasn't", 
		"way", "we", "we'd", "we'll", "we're", "we've", "welcome", "well", "went", "were", "weren't", "what", "what's", "whatever", "when", "whence", 
		"whenever", "where", "where's", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", 
		"who", "who's", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "won't", "wonder", "would", 
		"wouldn't", "yes", "yet", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "zero"
	};
    	
    private List<String> stopWordsList = Arrays.asList(stopWordsArray);

    public DocumentSet(String folder) {
    	// initialise document set
        termIndex = mapReduce( parseDocuments(folder) );
    }
    	
	private String stemWord(String word) {
		SnowballStemmer stemmer = null;

    	try {
	    	Class stemClass = Class.forName("snowball.englishStemmer");
	        stemmer = (SnowballStemmer) stemClass.newInstance();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	    stemmer.setCurrent(word);
        stemmer.stem();

        return stemmer.getCurrent();
	}

	private boolean isStopWord(String word) {
		// check is word is stop word
            
            if ( stopWordsList.contains(word)) {
		return true;
            }
		
	return false;
	}


	private Map<String, String> parseDocuments(String folderPath) {
        File[] files;
        double fileLength =0;
        // get a list of all the docs
        files = new File(folderPath).listFiles();
        fileCount = files.length;

        Map<String, String> map = new HashMap<String, String>();        
        
        // process the files
        for (File f : files) {
            StringBuilder str = new StringBuilder(""); // creates a string of document for HashMap

            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line;

                // reads a line at a time
                while ((line = reader.readLine()) != null) {
                    // split line into words (removes all punctuation & whitespace)
                    String[] wordsArray = line.split("[\\p{P} \\t\\n\\r]");
                     
                    for (String word : wordsArray) {
                    // for consistency etc.
                    word = word.toLowerCase();

                    if ( isStopWord(word) ) {
                            // if the word is a stop word, skip it
                            continue;
                    }
                    else {                                        
                        word = stemWord(word);

                        str.append(word).append(" ");
                        
                        //counting file length [will be added to the map key]
                        fileLength ++;                   
                    }
                }
            }// end while
                             
            } catch (IOException e) {
                e.printStackTrace();
            }

            // add to map
            map.put(f.getName(), str.toString());
            fileLengths.put(f.getName(), fileLength);
            fileLength = 0;
        } // end for
        
        return map;
    }


    // cpoied from MapReduce example class
    private Map<String, Map<String, Integer>> mapReduce(Map<String, String> input) {
        Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

        // MAP:               
        List<MappedItem> mappedItems = new LinkedList<MappedItem>();
                
        Iterator<Map.Entry<String, String>> inputIter = input.entrySet().iterator();
        while(inputIter.hasNext()) {
            Map.Entry<String, String> entry = inputIter.next();
            String file = entry.getKey();
            String contents = entry.getValue();
                               
            MapReduce.map(file, contents, mappedItems);
        }
        

        // GROUP:              
        Map<String, List<String>> groupedItems = new HashMap<String, List<String>>();
                       
        Iterator<MappedItem> mappedIter = mappedItems.iterator();
        while(mappedIter.hasNext()) {
            MappedItem item = mappedIter.next();
            String word = item.getWord();
            String file = item.getFile();
            List<String> list = groupedItems.get(word);
            if (list == null) {
                    list = new LinkedList<String>();
                    groupedItems.put(word, list);
            }
            list.add(file);
        }               


        // REDUCE:
        Iterator<Map.Entry<String, List<String>>> groupedIter = groupedItems.entrySet().iterator();
        while(groupedIter.hasNext()) {
            Map.Entry<String, List<String>> entry = groupedIter.next();
            String word = entry.getKey();
            List<String> list = entry.getValue();
                               
            MapReduce.reduce(word, list, output);
        }

        return output;
    }

    // process query list
    public List<String> processQuery(String[] query) {
    	List<String> queryList= new ArrayList<String>();
            //should return doc 24.txt first

            // query pre-processing
            for(String q : query) {
                q = q.toLowerCase();

            if ( isStopWord(q) ) {
                continue;
            }
            else {                                        
                q = stemWord(q);
                queryList.add(q);
            }
        }

        return queryList;
    }


     // getters
    public int fileCount() {
    	return fileCount;
    }
    public Map<String, Double> fileLengths() {
    	return fileLengths;
    }
    public Map<String, Map<String, Integer>> termIndex() {
    	return termIndex;
    }
}
