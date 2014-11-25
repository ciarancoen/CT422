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
    private static Map<String, String> querySet;

    private static String documentsPath = "";
    private static String queriesPath = "";
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
    public static void querySystem(String query, String docsPath) {
        // only create docSet once
        if ( docSet == null || !(docsPath.equals(documentsPath)) ) {
            long docStart = System.currentTimeMillis();
            documentsPath = docsPath;
            docSet = new DocumentSet(documentsPath);
            long docEnd = System.currentTimeMillis() - docStart;
            window.print("Document set created in " +docEnd +"ms.");
        }

        long start = System.currentTimeMillis();

        Map<String, Double> similarities = performQuery(query);

        // print results
        List<String> results = Maps.sortMapByKey(similarities);

        for (String s : results)
            window.print( s );

        long end = System.currentTimeMillis() - start;
        window.print("Results returned in " +end +"ms.\n");
    }


    // test the system using the query documents
    public static void testSystem(String qPath, String docsPath) {
        // only create docSet once
        if ( docSet == null || !(docsPath.equals(documentsPath)) ) {
            long docStart = System.currentTimeMillis();
            documentsPath = docsPath;
            docSet = new DocumentSet(documentsPath);
            long docEnd = System.currentTimeMillis() - docStart;
            window.print("Document set created in " +docEnd +"ms.");
        }

        // only create querySet once
        if ( querySet == null || !(qPath.equals(queriesPath)) ) {
            long qStart = System.currentTimeMillis();
            queriesPath = qPath;
            querySet = Queries.parseQueries(queriesPath);
            long qEnd = System.currentTimeMillis() - qStart;
            window.print("Query set created in " +qEnd +"ms.");
        }

        
        Iterator it = querySet.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String fileName = (String) entry.getKey();
            String query = (String) entry.getValue();

            // TODO: print results etc.
            performQuery(query);

        }   // end loop though queries
    }


    // query the documentSet
    private static Map<String, Double> performQuery(String queryInput) {
        List<String> query = Queries.processQuery(queryInput);

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

            double sim = Similarity.similarity(queryWeights, weight);
            double threshold = 0.0008;

            // only consider documents with a similarity abouve the threshold
            if (sim > threshold) {
                similarities.put( (String)entry.getKey(), sim );
            }
        }

        return similarities;
    }

}