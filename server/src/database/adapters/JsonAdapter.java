package database.adapters;

import java.util.Map;

import org.json.JSONObject;

public class JsonAdapter {
	
	public static JSONObject createNullJSON() {
		JSONObject json;
		json = new JSONObject();
		json.put("success", false);
		json.put("message", "Error occured");
		return json;
	}
	
	public static JSONObject createJSON(Map<String, Object> values) {
		JSONObject json;
		json = new JSONObject();
		
		if( values == null ) {
			return createNullJSON();
		}
		
		for(String key: values.keySet()) {
			json.put(key, values.get(key));
		}
		
		return json;
	}

}
