import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
public class JiraAddCommentsForIssueCreatedTesting {

	public static void main(String[] args) {
		RestAssured.baseURI="http://localhost:8080";
		// Login Scenario

		SessionFilter session = new SessionFilter();	
		given().log().all().header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"username\": \"ravikumar\",\r\n"
				+ "    \"password\": \"Ravi123456@\"\r\n"
				+ "}").filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();


		// Add Comment to existing Bug

		String expectedComments = "Hi how are you??";

		String addCommentResponse = given().pathParam("id", "10100")
				.log().all().header("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"body\": \""+expectedComments+"\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}").filter(session)
				.when().post("/rest/api/2/issue/{id}/comment")
				.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.get("id");



		// Add Attachment
		given().log().all().header("X-Atlassian-Token","no-check")
		.header("Content-Type", "multipart/form-data").pathParam("id", "10100").filter(session)
		.multiPart("file", new File("jira.txt") )
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);


		//	Get Issue
		String issueDetails = given().filter(session).pathParam("id", "10100").log().all()
				.when().get("/rest/api/2/issue/{id}").then().log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount =js1.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentsCount; i++) {
			String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId)) 
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();

				System.out.println(message);
				Assert.assertEquals(message, expectedComments);
			}
		}
	}

}
