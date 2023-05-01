
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import reqFiles.PayLoad;
import reqFiles.ReusbleMethods;

public class VerifyTotalTestNGEx {
	
	@Test
	public void verifyTotal() {
		
		JsonPath js = ReusbleMethods.rawToJason(PayLoad.CoursePrice());

		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount = " + purchaseAmount);
		int totalPrice = 0;
		for(int i = 0; i<js.getInt("courses.size()"); i++) {
			
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = price*copies;
			totalPrice = totalPrice+amount;
			
		}
		System.out.println("Total Price = "+ totalPrice);
		
		Assert.assertEquals(purchaseAmount, totalPrice);
		
		System.out.println("......This is TestNG Ex.......");
		
		
	}

}
