import org.testng.annotations.Test;

import POJOAddPlace.AddPlace;
import POJOAddPlace.Location;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerialisedInputPOJO {
	
	
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
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		
		String resp =given().queryParam("key","qaclick123")
		.body(ap)
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(resp);
		
	}
	

}
