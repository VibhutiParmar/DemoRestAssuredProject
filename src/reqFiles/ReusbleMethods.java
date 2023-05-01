package reqFiles;

import io.restassured.path.json.*;

public class ReusbleMethods {
	
	public static JsonPath rawToJason(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}

}
