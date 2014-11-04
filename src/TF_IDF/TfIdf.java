package TF_IDF;
/**
 *
 * @author Niall
 */
import java.util.List;

public class TfIdf { // /** * Calculated the tf of term termToCheck * 
//@param totalterms : Array of all the words under processing document * @param termToCheck : term of which tf is to be calculated. * @return tf(term frequency) of term termToCheck */ 
    
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
    public static double tfCalculator(String[] totalterms, String termToCheck) { 
        double count = 0; //to count the overall occurrence of the term termToCheck 
        for (String s : totalterms) { 
            if (s.equalsIgnoreCase(termToCheck)) { 
                count++; 
            } 
        } 

        return count / totalterms.length; 
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