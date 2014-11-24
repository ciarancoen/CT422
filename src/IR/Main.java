package IR;

import TF_IDF.*;

import java.util.List;
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
        
    // query the system with a user query
    public static void querySystem(String[] queryArray, String documentsPath) {
        // only create docSet once
        if ( docSet == null || !(documentsPath.equals(path)) ) {
            long docStart = System.currentTimeMillis();
            path = documentsPath;
            docSet = new DocumentSet(documentsPath);
            long docEnd = System.currentTimeMillis() - docStart;
            window.print("Document set created in " +docEnd +"ms.");
        }

        long start = System.currentTimeMillis();

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

        // print results
        List<Map.Entry> results = Maps.sortMap(similarities);

        for ( int i=0; i<10; i++ ) {
            window.print( results.get(i).getKey() );
        }

        long end = System.currentTimeMillis() - start;
        window.print("Results returned in " +end +"ms.\n");
    }


    // test the system using the query documents
    public static void testSystem(String querysPath, String documentsPath) {
        System.out.println(querysPath +" : " +documentsPath);

    }

}