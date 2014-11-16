package IR;

import IR.DocumentSet;
import TF_IDF.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;

// NOTE: to use the snowball stemmer, include the stemmer.jar in classpath
// when compiling and running

public class Main {
	public static void main(String[] args) {
            // folder for documents
            String documentsPath = "documents";
            DocumentSet docSet = new DocumentSet(documentsPath);

    //      Can use args[] from command line to input queries
            
//          Query #1: 1.txt
            String[] queryArray = {"the", "crystalline", "lens", "in", "vertebrates", "including", "humans"};
            List<String> query = Queries.processQuery(queryArray);

            TfIdf tfidf = new TfIdf(docSet.termIndex(), docSet.fileLengths(), docSet.fileCount());

            Map<String, Map<String, Double>> weights = new HashMap<String, Map<String, Double>>();
            Map<String, Double>queryWeightsMap = new HashMap<String, Double>();
            
            // Adds all term frequencies to same map:            
            for(int i=0;i<query.size();i++){
                weights.putAll(tfidf.tf_idf( query.get(i) )); //need to call it once for all terms in output map
                queryWeightsMap.putAll(tfidf.get_query_TFIDF_Map(query.get(i)));
            }

            System.out.println("\n\nQuery tfidf map: " + queryWeightsMap);

            printMap( Similarity.convertMap(weights) );
	}

        public static void printMap(Map<String, Map<String, Double>> map) {
        Iterator it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove();
        }
    }

}