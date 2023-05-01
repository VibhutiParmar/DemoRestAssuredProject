import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.*;
import reqFiles.PayLoad;
import reqFiles.ReusbleMethods;

public class UpdateAndValidatePlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Add Place -> Update Place with new address -> Get place to validate if New address is present in response

		//*Add Place - POST*//
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(PayLoad.AddPlace())
		.when().post("/maps/api/place/add/json\n")
		.then().assertThat().statusCode(200).header("server", "Apache/2.4.41 (Ubuntu)")
		.body("scope", equalTo("APP")).extract().response().asString();
		
		//System.out.println("Response :- " + response);
		
		JsonPath js = new JsonPath(response); //This will parse String to Json
		
		String PlaceID = js.getString("place_id");
		
		System.out.println(PlaceID);
		
		//*Update Place - PUT*//
		String NewAddress = "Joshipura, Junagadh";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\n"
				+ "\"place_id\":\""+PlaceID+"\",\n"
				+ "\"address\":\""+NewAddress+"\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place -> GET
		
		String GetPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceID)
		.when().get("maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jsg = ReusbleMethods.rawToJason(GetPlaceResponse);
		String actualAddress = jsg.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, NewAddress);
		
		
		
		
		
		
		
	}

}
