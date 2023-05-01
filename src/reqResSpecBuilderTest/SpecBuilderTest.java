package reqResSpecBuilderTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import java.util.*;

public class SpecBuilderTest {

	public static void main(String[] args) {
		
		GoogleAddPlace p = new GoogleAddPlace();
		
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		ArrayList<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		p.setLocation(loc);
		
		/*
		 	RestAssured.baseURI = "https://rahulshettyacademy.com";
		 	
		 	String response = given().log().all().body(p).queryParam("key", "qaclick123")
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
		 	
		 */
		
		Utility util = new Utility();
		
		RequestSpecification r = given().spec(util.RequestSpecBuilder()).body(p);
		
		String response = r.when().post("/maps/api/place/add/json")
		.then().spec(util.ResponseSpecBuilder()).extract().response().asString();
		
		System.out.println(response);
		

		
		

	}

}
