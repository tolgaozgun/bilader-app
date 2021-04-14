package database.adapters;

import java.util.HashMap;
import java.util.Map;

public class RequestAdapter {

	public static Map<String, String> convertParameters(Map<String, String[]> map, String[] keys) {

		Map<String, String> params;

		String paramValue;

		params = new HashMap<String, String>();

		if (map == null) {
			return null;
		}

		// Only accepted keys and their existing values are taking into consideration.
		for (String key : keys) {
			// Since our arrays will only contain one String, we can cast it into a String
			// by taking the first element.
			if (map.containsKey(key)) {
				paramValue = map.get(key)[0];
			} else {
				return null;
			}
			params.put(key, paramValue);
		}

		return params;
	}
}
