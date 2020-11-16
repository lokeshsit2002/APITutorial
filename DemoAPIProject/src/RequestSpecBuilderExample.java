import org.testng.annotations.Test;

import POJOAddPlace.AddPlace;
import POJOAddPlace.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class RequestSpecBuilderExample {
	
	
	@Test
	
	public void searialisedInputExample() {
		
		AddPlace ap = new AddPlace();
		
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, SNN 09");
		ap.setLanguage("French-IN");
		//set location
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);
		//
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		
		//set types
		List<String> tlist = new ArrayList<String>();
		tlist.add("shoe park");
		tlist.add("shop");
		ap.setTypes(tlist);
		//
		ap.setWebsite("http://google.com");
		
		
		RequestSpecification reqSpecBdr=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
		.addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();
		
		  ResponseSpecification responseSpecBdr = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification reqspec= given().spec(reqSpecBdr).body(ap);
		
		String resp =reqspec.when().post("maps/api/place/add/json")
		.then().assertThat().spec(responseSpecBdr).extract().response().asString();
		
		System.out.println(resp);
		
	}
	

}
