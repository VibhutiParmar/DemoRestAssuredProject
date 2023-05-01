package reqFiles;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;


public class TestSel {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "/Users/vivek/Desktop/Drivers/chromedriver_mac64/chromedriver"); WebDriver
		 * driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&service=lso&o2v=2&flowName=GeneralOAuthFlow"
		 * );
		 * driver.findElement(By.id("identifierId")).sendKeys("inboxtovibhuti@gmail.com"
		 * ); driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
		 * Thread.sleep(2000);
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(
		 * "Vibhu@1992");
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER
		 * );
		 * 
		 * String url = driver.getCurrentUrl();
		 */

	//	String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
	//	String partialcode = url.split("code=")[1];
	//	String code = partialcode.split("&scope")[0];
	//	System.out.println(code);
		String cd = "4%2F0AVHEtk5vJ8xO2mlDioxLEdEeU5kGRPHwG9jX6YpRqV--xV_qdEg1jsNM6Jx9rxhrC2QYmA";

		String response = given().urlEncodingEnabled(false)
				.queryParams("code", cd)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token")
				.then().extract().response()
				.asString();

		// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);
		String r2 = given().queryParams("contentType","application/json").queryParams("access_token", accessToken).auth().oauth2(accessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(r2);

	}

}
