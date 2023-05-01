package reqFiles;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	
	@Test(dataProvider="BooksData")
	public void AddBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166/";
		String response = given().log().all().header("Content-Type","application/json")
		.body(PayLoad.AddBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);;
		
		String Id = js.get("ID");
		
		System.out.println(Id);
		
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"hbzhc","378437"},{"vxjbv","88242"},{"dfjsfi","92428"}};
	}

}
