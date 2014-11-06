package TF_IDF;
/**
 *
 * @author Niall
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TfIdf { // /** * Calculated the tf of term termToCheck * 
//@param totalterms : Array of all the words under processing document * @param termToCheck : term of which tf is to be calculated. * @return tf(term frequency) of term termToCheck */ 
    static Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();
    
    public TfIdf(Map<String, Map<String, Integer>> output){
        this.output=output;
    }
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
    public double tfCalculator(String term, int docLength) {
//        double count = 0; //to count the overall occurrence of the term termToCheck 
//        for (String s : totalterms) { 
//            if (s.equalsIgnoreCase(termToCheck)) { 
//                count++; 
//            } 
//        }
        
        Set<Map.Entry<String, Integer>> entries = output.get(term).entrySet();
        String[] amountOfTermInDocument = null;
        for (Map.Entry entry : entries) {
            amountOfTermInDocument = entry.toString().split("=");
            System.out.println("entry: "+Arrays.toString(amountOfTermInDocument));
        }

        return Integer.parseInt(amountOfTermInDocument[1]) / docLength; 
    } // // /** * Calculated idf of term termToCheck * @param allTerms : all the terms of all the documents * @param termToCheck * @return idf(inverse document frequency) score */ 

//    IDF(t) = log(Total number of documents / Number of documents with term t in it).
                                //List of arrays: 
    public static double idfCalculator(List<String[]> allTerms, String termToCheck) { 
        double count = 0; 

        for (String[] ss : allTerms) {
            for (String s : ss) { 
                if (s.equalsIgnoreCase(termToCheck)) { 
                    count++; 
                    break; 
                }
            } 
        } 

        return Math.log(allTerms.size() / count); 
    }
}