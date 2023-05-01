package pojo;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import java.util.ArrayList;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Arrays;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		/*
		 * 
		 * Google do not allow to do automation of login
		 * System.setProperty("webdriver.chrome.driver",
		 * "/Users/vivek/Desktop/Drivers/chromedriver_mac64/chromedriver"); WebDriver
		 * driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&service=lso&o2v=2&flowName=GeneralOAuthFlow"
		 * );
		 * driver.findElement(By.id("identifierId")).sendKeys("inboxtovibhuti@gmail.com"
		 * ); driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
		 * Thread.sleep(2000);
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(
		 * "Vibhut@1992");
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(2000);
		 */
		// This is alternate way
		// Hit the URL manually and copy the URL after entering username and password
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress", "Protractor"};

		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AVHEtk5et6-6fGiVRtuuO_UeTRaBt-gpVLbRRT8SRST-a2YbL9xPL0DeB_EDKIIAGNHNgA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=6&prompt=none";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false).log().all().queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when()
				.post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response().asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String access_token = js.getString("access_token");

		GetCourse gcResponse = given().queryParam("access_token", access_token).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").then().extract().response()
				.as(GetCourse.class);

		System.out.println(gcResponse.getLinkedIn());
		System.out.println(gcResponse.getInstructor());

		System.out.println(gcResponse.getCourses().getApi().get(1).getCourseTitle());
		
	
		List<Api> listApi = gcResponse.getCourses().getApi();
		String expectedTitle = "SoapUI Webservices testing";
		
		int size = listApi.size();
		
		for(int i=0; i<size; i++) {
			String title = listApi.get(i).getCourseTitle();
			if(title.equalsIgnoreCase(expectedTitle)) {
				String price = listApi.get(i).getPrice();
				System.out.println(price);
				break;
			}
		}
		ArrayList<String> a = new ArrayList<String>();
		
		List<WebAutomation> webAutoList = gcResponse.getCourses().getWebAutomation();
		int sizeWeb = webAutoList.size();
		for(int i=0; i<sizeWeb; i++) {
			a.add(webAutoList.get(i).getCourseTitle());
		}
		
		List<String> expectedlist = Arrays.asList(courseTitles);
		Assert.assertEquals(a, expectedlist);
		//or Assert.assertTrue(a.equals(expectedlist));
		
	}

}
