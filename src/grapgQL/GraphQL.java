package grapgQL;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.*;


public class GraphQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Query
		String response = given().log().all().header("Content-Type" , "Application/Json")
				.body("{\"query\":\"query ($episodeId: Int!, $name: String) {\\n  episode(episodeId: $episodeId) {\\n    id\\n    name\\n    air_date\\n  }\\n  characters(filters: {name: $name}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n      species\\n      location{\\n        id\\n        name\\n      }\\n      origin {\\n        name\\n        type\\n        id\\n        residents {\\n          name\\n        }\\n      }\\n    }\\n  }\\n  charactersByIds(ids:2202){\\n    id\\n    name\\n    origin{\\n      name\\n    }\\n    location{\\n      name\\n    }\\n  }\\n}\\n\",\"variables\":{\"episodeId\":1,\"name\":\"Samrat\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphq"
				+ "l")
		.then().log().all().extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		//System.out.println(js.getString("data.charactersByIds[0]"));
		int count = js.getInt("data.characters.info.count");
		//System.out.println(count);
		Assert.assertEquals(count, 2);
		
		//Mutation
		String charName = "Chandani";
		String mutResponse =  given().log().all().header("Content-Type" , "Application/Json")
			.body("{\"query\":\"mutation($name: String!, $status: String!, $locName: String!){\\n  createCharacter(character:{name: $name, type:\\\"Indian Name\\\", status:$status, species:\\\"fantacy\\\", gender:\\\"Female\\\", image:\\\"png\\\", originId:2083, locationId:2083}){\\n    id\\n  }\\n  createLocation(location:{name: $locName, type: \\\"South\\\", dimension:\\\"123\\\"}){\\n    id\\n  }\\n}\\n\",\"variables\":{\"name\":\""+charName+"\",\"status\":\"alive\",\"locName\":\"Germany\"}}")
			.when().post("https://rahulshettyacademy.com/gq/graphq"
					+ "l")
			.then().log().all().extract().response().asString();
		
		System.out.println(mutResponse);

	}

}
