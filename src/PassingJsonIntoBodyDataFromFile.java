import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;

public class PassingJsonIntoBodyDataFromFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("/Users/vivek/Desktop/REST API/BodyData.json"))))
		.when().post("/maps/api/place/add/json\n")
		.then().log().all().assertThat().statusCode(200).header("server", "Apache/2.4.41 (Ubuntu)")
		.body("scope", equalTo("APP"));
		System.out.println("........Run Successfully........");
	}

}
