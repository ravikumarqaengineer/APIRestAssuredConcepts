import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;


public class basics2 {
	public static void main(String[] args) {
		
		//given - all input details
		//when - submit the Api -  resource and http methods
		//then - validate the response
			
	//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response

		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		JsonPath js=new JsonPath(response); // for parsing json 
		String placeId=js.get("place_id");
		
		System.out.println(placeId);

	}
}
