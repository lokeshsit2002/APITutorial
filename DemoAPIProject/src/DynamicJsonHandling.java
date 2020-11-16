import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJsonHandling {

	
	@Test(dataProvider="BookData")
	public void dynamicJson(String isbn, String aisle) {
		RestAssured.baseURI ="http://216.10.245.166/";
		
		String resp = given().header("Content-Type","application/json")
		.body(Payloads.bookDetails(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jsp = new JsonPath(resp);
		
		String idValue = jsp.get("ID");
		System.out.println("Id Value :"+idValue);
		
		//delete the books details from library
		
		String deleteResp =given().header("Content-Type","application/json")
		.body(Payloads.deleteBookDetails(idValue))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jsp1 = new JsonPath(deleteResp);
		 String msgValue = jsp1.getString("msg");
		
		 System.out.println("Delete Message Value :"+msgValue);
		
		
		
	}
	
	@DataProvider(name="BookData")
	 public Object[][] getBookData(){
		 return new Object[][] {
			{"Loku","002"},
			{"Yashi","002"},
			{"Khushi","002"}
		};
	}
	
	
}
