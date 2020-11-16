import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticJsonHandling {

	@Test
	public void staticJson() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166/";

		String resp = given().log().all().header("Content-Type", "application/json")
				.body(generateStringFromJsonFileSavedExternally(
						"C:\\Lokesh\\Personal\\RestAPI\\TestData\\AddBookDetails.json"))
				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println("Testing Testing Testing Testing");
		System.out.println(resp);

		JsonPath jsp = new JsonPath(resp);

		String idValue = jsp.get("ID");
		System.out.println("Id Value :" + idValue);

		// delete the books details from library

		String deleteResp = given().header("Content-Type", "application/json").body(Payloads.deleteBookDetails(idValue))
				.when().post("Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath jsp1 = new JsonPath(deleteResp);
		String msgValue = jsp1.getString("msg");

		System.out.println("Delete Message Value :" + msgValue);

	}

	public static String generateStringFromJsonFileSavedExternally(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));

	}

}