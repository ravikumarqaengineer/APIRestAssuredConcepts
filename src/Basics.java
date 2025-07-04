import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.payload;
public class Basics {

	public static void main(String[] args) {
		//validate if add place api is working as expected
		//given - all input details
		//when - submit the Api -  resource and http methods
		//then - validate the response
		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", containsString("Apache"));

	}

}
