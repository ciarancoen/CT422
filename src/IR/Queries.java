package IR;

import IR.Preprocessor;

import java.util.List;
import java.util.ArrayList;

public class Queries {
	// process query list
	public static List<String> processQuery(String[] query) {
		List<String> queryList= new ArrayList<String>();
        
        Preprocessor preprocessor = new Preprocessor();

        // query pre-processing
		for(String q : query) {
			q = q.toLowerCase();

			if ( preprocessor.isStopWord(q) ) {
				continue;
			}
			else {                                        
				q = preprocessor.stemWord(q);
				queryList.add(q);
			}
		}

		return queryList;
	}
}