import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import reqFiles.PayLoad;
import reqFiles.ReusbleMethods;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = ReusbleMethods.rawToJason(PayLoad.CoursePrice());
		//Print No of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		
		String title = js.getString("courses[0].title"); 
		// OR String titleOr = js.get("courses[0].title");
		System.out.println(title);
		
		//Print All course titles and their respective Prices
		System.out.println(title);
		
		for(int i = 0; i<count; i++) {
			String titleAll = js.getString("courses["+i+"].title" + " ");
			int price = js.getInt("courses["+i+"].price");
			System.out.println("Price for " +titleAll+ " is " +price+ ".");
			
		}
		
		//Print no of copies sold by RPA Course
		
		for(int i = 0; i<count; i++) {
			String titleAll = js.getString("courses["+i+"].title" + " ");
			if(titleAll.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println("No of copies of RPA is: "+copies);
				break;
			}
			
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount = " + purchaseAmount);
		int totalPrice = 0;
		for(int i = 0; i<count; i++) {
			
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = price*copies;
			totalPrice = totalPrice+amount;
			
		}
		System.out.println("Total Price = "+ totalPrice);
		
		Assert.assertEquals(purchaseAmount, totalPrice);
		

		
	}

}
