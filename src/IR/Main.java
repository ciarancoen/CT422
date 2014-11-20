package IR;

import TF_IDF.*;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.awt.EventQueue;

// NOTE: to use the snowball stemmer, include the stemmer.jar in classpath
// when compiling and running

public class Main {
    private static DocumentSet docSet;
    private static String path = "";
    private static GUI window;

	public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                            window = new GUI();
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                }
		});
        }
        
    public static void querySystem(String[] queryArray, String documentsPath) {
        // only create docSet once
        if ( docSet == null || !(documentsPath.equals(path)) ) {
            window.print("Document set created...");
            path = documentsPath;
            docSet = new DocumentSet(documentsPath);
        }


//          Query #1: 1.txt
        List<String> query = Queries.processQuery(queryArray);

        TfIdf tfidf = new TfIdf(docSet.termIndex(), docSet.fileLengths(), docSet.fileCount(), query);

        Map<String, Map<String, Double>> weights = new HashMap<String, Map<String, Double>>();
        Map<String, Double> queryWeights = new HashMap<String, Double>();

        // Adds all term frequencies to same map:            
        for (int i = 0; i < query.size(); i++) {
            weights.putAll(tfidf.tf_idf(query.get(i)));
            queryWeights.putAll( tfidf.get_query_TFIDF_Map(query.get(i)) );
        }

        // loop through the converted map and find similarities
        Iterator it = Similarity.convertMap(weights).entrySet().iterator();
        Map<String, Double> similarities = new HashMap<String, Double>();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Map weight = (HashMap) entry.getValue();

            // filename & similarity with query
            similarities.put( (String)entry.getKey(), Similarity.similarity(queryWeights, weight) );
        }

        window.print( sortMap(similarities) );
    }
    

    public static void printMap(Map<String, Map<String, Double>> map) {
        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            System.out.println(entry);
        }
    }

    // returns the map entries in sorted order
    private static List<String> sortMap(Map map) {
        List<String> sorted = new LinkedList<String>(map.entrySet());

        Collections.sort(sorted, new Comparator() {
            public int compare(Object entry1, Object entry2) {
                double d1 = (double) ((Map.Entry) entry1).getValue();
                double d2 = (double) ((Map.Entry) entry2).getValue();

                return Double.compare(d2, d1);
            }
        });

        return sorted;
    }

}