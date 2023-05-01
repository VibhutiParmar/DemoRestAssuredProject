package EcommerceAPITesting;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import java.io.File;
import org.testng.Assert;
import java.util.*;
import pojoForECommAPI.CreateOrderResponse;
import pojoForECommAPI.CreateProductReaponse;
import pojoForECommAPI.GetOrder;
import pojoForECommAPI.LoginRequest;
import pojoForECommAPI.LoginResponse;
import pojoForECommAPI.Order;
import pojoForECommAPI.OrderDetails;
import io.restassured.path.json.*;

public class EcomAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// .................LogIN.................//

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setUserEmail("vibhutiparmar19@gmail.com");
		loginrequest.setUserPassword("Register@123");

		RequestSpecification reqlogin = given().log().all().spec(req).body(loginrequest);

		LoginResponse loginResponse = reqlogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);

		String token = loginResponse.getToken();
		String userID = loginResponse.getUserId();

		System.out.println(token);
		System.out.println(userID);

		// .................Create Product.................//

		RequestSpecification createProductBaseSpec = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).build();

		RequestSpecification createProductreq = given().log().all().spec(createProductBaseSpec)
				.param("productName", "Sandal").param("productAddedBy", userID).param("productCategory", "fasion")
				.param("productSubCategory", "Footwear").param("productPrice", "6500")
				.param("productDescription", "Paragoan Original").param("productFor", "Women")
				.multiPart("productImage", new File("/Users/vivek/Desktop/REST API/purse.jpeg"));

		CreateProductReaponse response = createProductreq.when().post("/api/ecom/product/add-product").then().log()
				.all().extract().response().as(CreateProductReaponse.class);

		System.out.println(response);

		String productID = response.getProductId();
		String message = response.getMessage();

		System.out.println(productID);
		System.out.println(message);

		// .................Create Order.................//

		RequestSpecification CreateOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();

		OrderDetails od = new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productID);
		// String productOrderedId = od.getProductOrderedId();

		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsList.add(od);

		Order order = new Order();
		order.setOrders(orderDetailsList);

		RequestSpecification createOrderreq = given().log().all().spec(CreateOrderBase).body(order);

		CreateOrderResponse createOrderresponse = createOrderreq.when().post("/api/ecom/order/create-order").then()
				.log().all().extract().response().as(CreateOrderResponse.class);

		System.out.println(createOrderresponse);

		/*
		 * int[] orderID = createOrderresponse.getOrders(); for(int i =0;
		 * i<orderID.length; i++) { System.out.println(orderID[i]); }
		 */
		System.out.println("......................");
		String orderID = createOrderresponse.getOrders()[0];
		System.out.println(orderID);
		System.out.println("......................");

		// .................Delete Order.................//

		RequestSpecification DeleteOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();

		RequestSpecification deleteOrdertreq = given().log().all().spec(DeleteOrderBase).pathParam("productID",
				productID);

		String deleteOrderResponse = deleteOrdertreq.when().delete("/api/ecom/product/delete-product/{productID}")
				.then().log().all().extract().response().asString();

		System.out.println(deleteOrderResponse);

		JsonPath js = new JsonPath(deleteOrderResponse);
		String exMessage = "Product Deleted Successfully";
		String deleteMessage = js.getString("message");
		Assert.assertEquals(deleteMessage, exMessage);

		// .................Get Order Details.................//

		/*
		RequestSpecification getOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		RequestSpecification getOrdertreq = given().log().all().spec(getOrderBase).queryParam("id", orderID);

		String getOrderResponse = getOrdertreq.when().get("/api/ecom/order/get-orders-details").then().log().all()
				.extract().response().asString();

		System.out.println(getOrderResponse);
		JsonPath jsr = new JsonPath(getOrderResponse);
		System.out.println(jsr.getString("data.country"));
		*/
		
		RequestSpecification getOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification getOrdertreq = given().log().all().spec(getOrderBase).queryParam("id", orderID);
		
		GetOrder res = getOrdertreq.when().get("/api/ecom/order/get-orders-details")
			.then().log().all().extract().response().as(GetOrder.class);
		
		String msg = res.getMessage();
		System.out.println(msg);
		
		
	

	}

}
