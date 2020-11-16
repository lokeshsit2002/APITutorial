import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payloads;

public class Basics001 {

	public static void main(String arg[]) {
		
		//given: All input details
		//when: Submit the API( post+resource)
		//then: Validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.addPlaceDetails())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response is " +response);
		
		JsonPath jsp= new JsonPath(response);//For parsing Json
		
		String placeId= jsp.getString("place_id");
		
		System.out.println("PalceID is :"+ placeId);
		
		
		//Put API to update the place name
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\"70 Summer walk, USA\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//validate the updated place name using GET API
		
		String newAddress="70 Summer walk, USA";
		
		//1st method validating through RestAssured Assert Method
		/*
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("address",equalTo(newAddress))
		.header("Server", "Apache/2.4.18 (Ubuntu)");
		*/
		
		//2nd method validating through TestNG Assert method
		
		String getResponseText=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		JsonPath jsp1= new JsonPath(getResponseText);
		String actualAddress= jsp1.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
		
		
		
		
	}
}
