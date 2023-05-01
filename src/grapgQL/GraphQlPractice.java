package grapgQL;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.*;


public class GraphQlPractice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="https://hasura.io/learn/graphql";
		String auth = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDY0NGM0ZTYxYTkyMmJjZWE4NGY5MzM4NCJ9LCJuaWNrbmFtZSI6ImluYm94dG92aWJodXRpIiwibmFtZSI6ImluYm94dG92aWJodXRpQGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci84M2ZiOWRiMmM2Y2FkYjE1OTc5MmFkZTljY2Y4NjVmZT9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmluLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDIzLTA0LTI4VDIyOjUzOjIyLjIzMFoiLCJpc3MiOiJodHRwczovL2dyYXBocWwtdHV0b3JpYWxzLmF1dGgwLmNvbS8iLCJhdWQiOiJQMzhxbkZvMWxGQVFKcnprdW4tLXdFenFsalZOR2NXVyIsImlhdCI6MTY4MjcyMjQwMywiZXhwIjoxNjgyNzU4NDAzLCJzdWIiOiJhdXRoMHw2NDRjNGU2MWE5MjJiY2VhODRmOTMzODQiLCJhdF9oYXNoIjoiZ2JXSGJTRjRmOTFYR3Y2MVQtZ3pOQSIsInNpZCI6IkpjcU9XbWRrV1pWTnhuUTJST0pTdDEzYzN6T1VpMEpRIiwibm9uY2UiOiJHbHdycHphTXN0QjNGRUdTYmtZSkdFX3ZoLWZrS0pOVSJ9.TECgUKtbwykZlxPZniXtJwN8x1ERsR7lUFPLGsDnz0QbiwJzhejOiTEa9cpdQVhn9CdIs225U0kDtYKwzkrmbSk6Lncn8CUaT8Y5YdtCWSuoXdf4zNNaoKQtm_2z4XGAADvBP3FH2BlLRf6CL_AXUH8rQbr1cnCEPogWf0RrsDAi_KmsKxV14TQUL3ms-aSvkcRgU8BFUgvTx0EFLd35GG7UOJBzL2WPN3UbebgvihuqhQ5X_L0cgu03Aqpm8DXn6-XoGkznBgtyaYsQ-mA7QfhO_Vmhw0F2DotdhmZUFT16WMEVf5U6tNrDZm13bPP4zYuyHwMAYU8JLTX2F4o-ow";
		
		String response = given().log().all().contentType("application/json").header("Authorization", auth)
		.body("{\"query\":\"{\\n  users(limit: 2) {\\n    id\\n  }\\n  online_users {\\n    id\\n    last_seen\\n    user {\\n      id\\n      name\\n    }\\n  }\\n  todos_by_pk(id: 1) {\\n    created_at\\n    id\\n  }\\n}\\n\",\"variables\":null}")
		.when().post()
		.then().log().all().extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		
		System.out.println(js.getString("data.users[1].id"));
		
	}

}
