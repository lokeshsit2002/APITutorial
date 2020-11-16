package MainJAVAMethodClasses;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.app.qa.resources.PayloadBuilds;
import com.app.qa.resources.restAssuredDataDrivenExcel;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class RestAssuredJSONMapExcel {
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException  {
		
		
		RestAssured.baseURI ="http://216.10.245.166/";
		
		restAssuredDataDrivenExcel rAssuredDataExcel = new restAssuredDataDrivenExcel();
		ArrayList<String> bookDetailsList=rAssuredDataExcel.getExcelData("BookDetails");
		
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		
/*		System.out.println(bookDetailsList.get(1));
		System.out.println(bookDetailsList.get(2));
		System.out.println(bookDetailsList.get(3));
		System.out.println(bookDetailsList.get(4));
		
*/
		jsonMap.put("name", bookDetailsList.get(1));
		jsonMap.put("isbn", bookDetailsList.get(2));
		jsonMap.put("aisle", bookDetailsList.get(3));
		jsonMap.put("author", bookDetailsList.get(4));
		
		
		String resp = given().header("Content-Type","application/json")
		.body(jsonMap)
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jsp = new JsonPath(resp);
		
		String idValue = jsp.get("ID");
		System.out.println("Id Value :"+idValue);
		
		//delete the books details from library
		
		String deleteResp =given().header("Content-Type","application/json")
		.body(PayloadBuilds.deleteBookDetails(idValue))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jsp1 = new JsonPath(deleteResp);
		 String msgValue = jsp1.getString("msg");
		
		 System.out.println("Delete Message Value :"+msgValue);
		
		
		
	}

}
