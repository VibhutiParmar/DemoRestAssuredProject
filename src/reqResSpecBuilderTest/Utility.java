package reqResSpecBuilderTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import io.restassured.http.ContentType;
import io.restassured.specification.*;
public class Utility {
	
	public RequestSpecification RequestSpecBuilder() {
		RequestSpecification req =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
	 .addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		return req;
	}
	
	public ResponseSpecification ResponseSpecBuilder() {
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return res;
	}

}
