import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle )
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
				body(payload.addBook(isbn, aisle))
				.when().post("/Library/Addbook.php").
				then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}

	@Test(dataProvider="BookId")
	public void deleteBook(String ID)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json")
		.body(payload.deleteBook(ID))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();	
		JsonPath js = ReUsableMethods.rawToJson(response);
		String msg = js.get("msg");
		System.out.println(msg);
	}


	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		// array = collection of elements
		// multidimensional array = collection of arrays
		return new Object[][] {{"bsgf","456812"},{"sbsdvb","54564242"},{"dshfbf","5424512"}};
	}


	@DataProvider(name="BookId")
	public Object [][] getId()
	{
		return new Object [][] {{"bsgf456812"},{"sbsdvb54564242"},{"dshfbf5424512"}};
	}
	
	//content of the file to string --> content of file can convert into byte --> byte data to string
	
	@Test
	public void addBook1() throws IOException
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json").
				body(new String (Files.readAllBytes(Paths.get("G:\\resource notes on Rest assured\\AddBook.json"))))
				.when().post("/Library/Addbook.php").
				then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@Test
	public void deleteBook1() throws IOException
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type","application/json")
		.body(new String (Files.readAllBytes(Paths.get("G:\\resource notes on Rest assured\\DeleteBook.json"))))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();	
		JsonPath js = ReUsableMethods.rawToJson(response);
		String msg = js.get("msg");
		System.out.println(msg);
	}

}