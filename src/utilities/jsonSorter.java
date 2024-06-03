package utilities;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class jsonSorter {
    public static List<JSONObject> sortJsonListByKey(List<JSONObject> jsonList, String key) {
        return jsonList.stream()
                .sorted((a, b) -> {
                    String valueA =  a.get(key).toString();
                    String valueB =  b.get(key).toString();
                    try {
                        double doubleValueA = Double.parseDouble(valueA);
                        double doubleValueB = Double.parseDouble(valueB);
                        return Double.compare(doubleValueA, doubleValueB);
                    } catch (NumberFormatException e) {
                        return valueA.compareTo(valueB);
                    }
                })
                .collect(Collectors.toList());
    }
}
