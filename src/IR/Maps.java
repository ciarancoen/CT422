package IR;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Maps {
	public static void printMap(Map<String, Map<String, Double>> map) {
		Iterator it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			System.out.println(entry);
		}
	}

    // returns the map entries in sorted order
	public static List<Map.Entry> sortMap(Map map) {
		List<Map.Entry> sorted = new ArrayList<Map.Entry>(map.entrySet());

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