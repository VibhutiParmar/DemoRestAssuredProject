package reqFiles;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.*;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "http://localhost:8080";

		// Login Scenario

		SessionFilter session = new SessionFilter();
		String loginResponse = given().log().all().header("Content-Type", "application/json")
				.body("{ \"username\": \"inboxtovibhuti\", " + "\"password\": \"inboxtovibhuti@12345\" }")
				.filter(session).when().post("/rest/auth/1/session").then().extract().response().asString();

		System.out.println("...........................................");

		// Add Comment

		String expectedResponse = "Hi, How are you? Hope you are doing well...!!!";
		given().log().all().pathParam("id", "10006").header("Content-Type", "application/json")
				.body("{\n" + "    \"body\": \"" + expectedResponse + "\",\n" + "    \"visibility\": {\n"
						+ "        \"type\": \"role\",\n" + "        \"value\": \"Administrators\"\n" + "    }\n" + "}")
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat()
				.statusCode(201);

		System.out.println("...........................................");

		// Add Attachment

		String addCommentResponse = given().log().all().header("X-Atlassian-Token", "no-check").filter(session)
				.pathParam("id", "10006").header("Content-Type", "multipart/form-data")
				.multiPart("file", new File("Jira.txt")).when().post("/rest/api/2/issue/{id}/attachments").then().log()
				.all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);

		System.out.println("...........................................");

		String commentID = js.getString("id");
		System.out.println(commentID);

		System.out.println("...........................................");

		// Get Issue

		String issueDetails = given().filter(session).log().all().pathParam("id", "10006")
				.queryParam("fields", "comment").when().get("/rest/api/2/issue/{id}").then().log().all().extract()
				.response().asString();

		System.out.println(issueDetails);
		System.out.println("...........................................");

		JsonPath js1 = new JsonPath(issueDetails);

		int commentsCount = js1.getInt("fields.comment.comments.size()");
		System.out.println(commentsCount);

		System.out.println("...........................................");

		for (int i = 0; i < commentsCount; i++) {
			String cID = js1.getString("fields.comment.comments[" + i + "].id");
			// System.out.println("get Issue comment ID = " + cID);

			if (commentID.equalsIgnoreCase("["+cID+"]")) {
				String bodyMessage = js1.getString("fields.comment.comments[" + i + "].body");
				System.out.println(bodyMessage);
				Assert.assertEquals(bodyMessage, expectedResponse);
			}

		}
	}

}
