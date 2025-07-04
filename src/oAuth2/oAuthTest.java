package oAuth2;

/*import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;*/

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {

	/*	System.setProperty("webdriver.chrome.driver", "./softwares/chromedriver.exe");	
		WebDriver driver= new ChromeDriver();	

		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");

		//tagname[attribute='value']

		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ravikumarifocus1");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Ravi123456@");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url =driver.getCurrentUrl();  */

		String url = ("https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qjFyHkoloXed08ngAQ1bAedQlkjpR5uMNsjV8bMTgCCP4eYUTozRPwS2QYey8kNow&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none");

		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);



		String accessTokenResponse = given().urlEncodingEnabled(false)
				.queryParams("code",code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code")
				
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accesssToken = js.getString("access_token");
		
		
		String response = given().queryParam("access_token", accesssToken)

				
				.when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.then().log().all().extract().response().asString();
				
		
		System.out.println(response);

	}

}
