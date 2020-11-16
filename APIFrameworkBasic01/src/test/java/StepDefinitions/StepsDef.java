package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.qa.pojo.testdata.AddPlace;
import com.app.qa.pojo.testdata.Location;
import com.app.qa.resources.APIConstantResources;
import com.app.qa.resources.PayloadBuilds;
import com.app.qa.resources.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepsDef extends Utils{
	
	RequestSpecification reqspec;
	Response resp;
	PayloadBuilds pload = new PayloadBuilds();
	public static String placeId;
	
	 @Given("^Add Place Payload with (.+),(.+),(.+)$")
	    public void add_place_payload_with_(String name, String language, String address) throws IOException{
				
		reqspec = given().spec(requestSpecification()).body(pload.addPlacePayload(name,language,address));
	}

	 @When("^User calls \"([^\"]*)\" to \"([^\"]*)\" http add request$")
	    public void user_calls_resources_to_something_http_add_request(String resource, String methodhttp) {
		 
		 //Constructor will be called with value of the Resource which you pass
		 APIConstantResources APIresource= APIConstantResources.valueOf(resource);
		 if(methodhttp.equalsIgnoreCase("POST")) {
			 resp =reqspec.when().post(APIresource.getAPIConstantResources())
						.then().assertThat().spec(responseSpecification()).extract().response();
		 }
		 else if(methodhttp.equalsIgnoreCase("GET")) {
			 resp =reqspec.when().get(APIresource.getAPIConstantResources())
						.then().assertThat().spec(responseSpecification()).extract().response();
		 }
		
				
				
	}

	@Then("The API call gets success with status code {int}")
	public void the_API_call_gets_success_with_status_code(int statusValue) {
	  assertEquals(resp.getStatusCode(),statusValue);
	}

	@Then("The {string} of response body is {string}")
	public void the_of_response_body_is(String keyValue, String expectedKeyValue) {
		assertEquals(getValueUsingKeyAndJsonPath(resp,keyValue),expectedKeyValue);
	}

	 @And("^Validate the PlaceId mapped to the (.+) using \"([^\"]*)\"$")
	    public void validate_the_placeid_mapped_to_the_using_something(String expectedName, String resource) throws IOException  {
	     placeId= getValueUsingKeyAndJsonPath(resp,"place_id")  ; 
	     
	     reqspec =given().spec(requestSpecification()).queryParam("place_id",placeId);
	     user_calls_resources_to_something_http_add_request(resource, "GET");
		 String actualName= getValueUsingKeyAndJsonPath(resp,"name");
		 assertEquals(actualName,expectedName);
	    }
	 
	 
	 @Given("Delete Place Payload")
	 public void delete_Place_Payload() throws IOException {
		 reqspec=  given().spec(requestSpecification())
				 .body(pload.deletePayload(placeId));
		 
	    }
}
