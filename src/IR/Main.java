package IR;

import IR.Helpers;
import MapReduce.*;
import java.lang.reflect.Method;
import snowball.*;
import java.io.*;
import java.lang.StringBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import TF_IDF.TfIdf;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Set;


// NOTE: to use the snowball stemmer, include the stemmer.jar in classpath
// when compiling and running

public class Main {
    static int fileCount =0;
    static Map<String, Double> fileLengths = new HashMap<String, Double>();
    
	public static void main(String[] args) {
        // folder for documents
        String documentsPath = "documents";

        // input & output for MapReduce
        Map<String, String> input = new HashMap<String, String>();
        Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();
        
//      Output of TFIDF calculations: Key = term, value= TFIDF(term)
        
        Map<String, Map<String, Integer>> termFreqMap = new HashMap<String, Map<String, Integer>>();

        input = parseDocuments(documentsPath);
        output = mapReduce(input);

        String[] queryArray = {"patient"};//can use something like this for storing all the query terms
        // get returns Map<String, Integer>
        // some test words

        TfIdf tfidf = new TfIdf(output);//1 TfIdf object can be used for all the indexing
        double[] idf_array = new double[queryArray.length];
        
        //NEED TO ADD TO THIS MAP NOT OVERWRITE IT!!!
        Map<String, Map<String, Double>> tf = tfidf.tfCalculator(queryArray[0], fileLengths);//need to call it once for all terms in output map
//        System.out.println(tf);
//        System.out.println(output.get(queryArray[0]).entrySet().size());//returns total amount of relevant documents
        idf_array[0] = tfidf.idfCalculator(fileCount, queryArray[0]);
//        Object[] arr = output.entrySet().toArray();
        System.out.println(Arrays.toString(idf_array));
//        System.out.println("total files: "+fileCounter);
//        String[] str = null;
//        double count = TfIdf.tfCalculator(str, "skin");
//        System.out.println("size: "+output.get("medica").size());//count occurrences of each word
//        System.out.println(output.get("placenta") +"\n");
//        System.out.println(output.get("blood") +"\n");

	}
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
//    IDF(t) = log(Total number of documents / Number of documents with term t in it).

    public static Map<String, String> parseDocuments(String folderPath) {
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

                    if ( Helpers.isStopWord(word) ) {
                            // if the word is a stop word, skip it
                            continue;
                    }
                    else {                                        
                        word = Helpers.stemWord(word);

                        str.append(word).append(" ");
                        
                        //counting file length [will be added to the map key]
                        fileLength ++;                   
                    }
                }
//                    System.out.println(f.getName()+Arrays.toString(wordsArray));
            }// end while
                             
            } catch (IOException e) {
                e.printStackTrace();
            }

            // add to map
            map.put(f.getName(), str.toString());
            fileLengths.put(f.getName(), fileLength);
            fileLength =0;//reset counter
        } // end for
        
        return map;
    }


    // cpoied from MapReduce example class
    public static Map<String, Map<String, Integer>> mapReduce(Map<String, String> input) {
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


}