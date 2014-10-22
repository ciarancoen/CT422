package IR;

import IR.Helpers;
import java.lang.reflect.Method;
import snowball.*;
import java.util.Hashtable;
import java.io.*;
import java.util.List;
import java.util.ArrayList;


// NOTE: to use the snowball stemmer, include the stemmer.jar in classpath
// when compiling and running

public class Main {
	public static void main(String[] args) {
    	File[] files;
    	List<String> wordsList = new ArrayList<String>();

    	// get a list of all the docs
    	files = new File("documents").listFiles();

    	// process the files
    	for (File f : files) {
    		System.out.println(f.getName());

    		try {
    			BufferedReader reader = new BufferedReader(new FileReader(f));
    			String line;

    			// reads a line at a time
    			while ((line = reader.readLine()) != null) {
                             //Getting some unknown characters from InfoManagement.txt cos of the encoding. Could use this instead? readAllLines(Path path, Charset cs) throws IOException
                            // split line into words (removes all punctuation & whitespace)
                            String[] wordsArray = line.split("[\\p{P} \\t\\n\\r]");

                            for (String word : wordsArray) {
                                // for consistency etc.
                                word = word.toLowerCase();

                                // TODO: perform stopword removal (replace 'false' with check)
                                if ( Helpers.isStopWord(word) ) {
                                        // if the word is a stop word, skip it
                                        continue;
                                }
                                else {                                        
                                    word = Helpers.stemWord(word);

                                    // TODO: Replace ArrayList wordsList with a hash table
                                    // First need to figure out what to index...
                                    wordsList.add(word);
                                }
                            }	
			}// end while
                        
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

    	} // end for

    	for (String s : wordsList) {
    		System.out.println(s);
    	}
	}// end main
}