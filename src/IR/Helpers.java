package IR;

import java.lang.reflect.Method;
import snowball.*;
import java.util.Hashtable;
import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class Helpers {
	public static String stemWord(String word) {
		SnowballStemmer stemmer = null;

    	try {
	    	Class stemClass = Class.forName("snowball.englishStemmer");
	        stemmer = (SnowballStemmer) stemClass.newInstance();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	    stemmer.setCurrent(word);
        stemmer.stem();

        return stemmer.getCurrent();
	}
}