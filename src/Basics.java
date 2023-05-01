import io.restassured.RestAssured;
import reqFiles.PayLoad;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Validate if Add Place API is working as expected
		//Given - All input details what u need to submir for the API
		//When - Submit the API -> resourse and http method
		//Them - Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(PayLoad.AddPlace())
		.when().post("/maps/api/place/add/json\n")
		.then().log().all().assertThat().statusCode(200).header("server", "Apache/2.4.41 (Ubuntu)")
		.body("scope", equalTo("APP"));
		
		//Add Place -> Update Place with new address -> Get place to validate if New address is present in response
		
		
		
		

	}

}
