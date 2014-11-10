package IR;

import IR.DocumentSet;
import TF_IDF.TfIdf;
import java.lang.StringBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// NOTE: to use the snowball stemmer, include the stemmer.jar in classpath
// when compiling and running

public class Main {
	public static void main(String[] args) {
            // folder for documents
            String documentsPath = "documents";
            DocumentSet docSet = new DocumentSet(documentsPath);

    //      Can use args[] from command line to input queries
            String[] queryArray = {"the", "crystalline", "lens", "in", "vertebrates", "including", "humans"};
            List<String> query = Queries.processQuery(queryArray);

            TfIdf tfidf = new TfIdf(docSet.termIndex(), docSet.fileLengths(), docSet.fileCount());

            // Adds all term frequencies to same map
            Map<String, Map<String, Double>> weights = new HashMap<String, Map<String, Double>>();

            for(int i=0;i<query.size();i++){
                weights.putAll(tfidf.tf_idf( query.get(i) )); //need to call it once for all terms in output map
            }

            System.out.println("\n\ntfidf map: " + weights);
	}
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
//    IDF(t) = log(Total number of documents / Number of documents with term t in it).

}