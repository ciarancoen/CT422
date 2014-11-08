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
        //Only passing this huge hashmap in once for efficiency
        this.output=output;
    }
//    TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document)
    public Map<String, Map<String, Double>> tfCalculator(String term, Map<String, Double> fileLengths) {
        //This method will output the term frequence for a term in 1 document.
        Map<String, Map<String, Double>>resultMap=new HashMap<String, Map<String, Double>>();
        Map<String, Double>termFreqFileName=new HashMap<String, Double>();
        double docLength;
        String filename;
        Set<Map.Entry<String, Integer>> entries = output.get(term).entrySet();//returns only the term set        
        double TF;
        
//        e.g.
//        entry1: [368.txt, 1]
//        entry2: [374.txt, 1]
//        entry3: [25.txt, 1]
        
//        System.out.println(fileLengths.toString());
        for (Map.Entry entry : entries) {//loop thru all occurrences of term            
            String[] amountOfATermInADocument = entry.toString().split("=");//split into array of filename & term count
            filename=amountOfATermInADocument[0];
//            System.out.println(Arrays.toString(amountOfATermInADocument));
//            System.out.println("\""+amountOfATermInADocument[0]+"\"");
            
//            System.out.println(fileLengths.get(amountOfATermInADocument[0]));
            docLength=fileLengths.get(filename);
//            System.out.println("docLength: "+docLength);
            TF = Double.parseDouble(amountOfATermInADocument[1]) / docLength;
//            System.out.println("TF: "+TF);
            termFreqFileName.put(filename, TF);
        }
//        System.out.println(termFreqFileName);
        resultMap.put(term, termFreqFileName);
//        System.out.println(resultMap);
        return resultMap;
    } // // /** * Calculated idf of term termToCheck * @param allTerms : all the terms of all the documents * @param termToCheck * @return idf(inverse document frequency) score */ 

//    IDF(t) = log(Total number of documents / Number of documents with term t in it).
                                //List of arrays: 
    public double idfCalculator(int docCount, String query) { 
        double relevantDocCount = output.get(query).entrySet().size();
//        System.out.println("docCount:"+docCount+", relevantDocCount:"+relevantDocCount+", result:"+Math.log10(docCount / relevantDocCount));
        return Math.log10(docCount / relevantDocCount); 
    }
}