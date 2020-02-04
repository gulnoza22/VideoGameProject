package restapi;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.HashMap;

public class RestApiAutomation {

//	Testing GET request with BDD approach in Rest API
	@Test(priority = 1) // PASSED
	public void testGetRequest_getAllVideoGames_TC1() {

		given()

				.when().get("http://localhost:8080/app/videogames")

				.then().statusCode(200);
	}

	// Testing POST request with BDD approach in Rest API
	@Test(priority = 2) // PASSED
	public void testPost_toAddNewVideoGame() {
		// Creating a new datas in API by using POST method
		HashMap data = new HashMap();
		data.put("id", "19");
		data.put("name", "Tourist");
		data.put("releaseDate", "2020-01-25T06:01:13.418Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response res = given().contentType("application/json")
				.body(data)// it will show the data's in the payload
				.when()
				.post("http://localhost:8080/app/videogames")
				.then()
				.statusCode(200)
				.log().body()// this log is printing the body in the console
				.extract().response();

		String jsonString = res.asString();// getting a success string message

		// verifying success message--> "Record Added Successfully"
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);

	}

	// Testing with GET request to get the Created VideoGame ID
	@Test(priority = 3) // PASSED
	public void testGet_toGetCreatedVideoID() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames/19")
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("19")).body("videoGame.name", equalTo("Tourist"));

	}
    
	//Updating with PUT method an existing videoGame
	@Test(priority = 4) //PASSED
	public void testPUT_toUpdateVideoGame() {
		
		HashMap data1 = new HashMap();
		data1.put("id", "19");
		data1.put("name", "BatMan");
		data1.put("releaseDate", "2020-01-25T06:01:13.418Z");
		data1.put("reviewScore", "10");
		data1.put("category", "Adventure");
		data1.put("rating", "Universal");
		
		given()
		.contentType("application/json")
		.body(data1)
		.when()
		.put("http://localhost:8080/app/videogames/19")
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("19"))
		.body("videoGame.name", equalTo("BatMan"));
		

	}
	
	//Deleting videoGame ID with delete method.
     @Test(priority=5)//PASSED
	public void testDelete_toDeleteVideoGameID() {
    	 Response res =given()
		.when()
		.delete("http://localhost:8080/app/videogames/19")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}
}