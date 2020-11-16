package StepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	
	public void generatePlaceIdForDelete() throws IOException {
		StepsDef sd= new StepsDef();
		if(StepsDef.placeId== null) {
			sd.add_place_payload_with_("Hindustan Times", "Hindi","New Delhi SafdarGanj Marg");
			sd.user_calls_resources_to_something_http_add_request("AddPlaceAPI","POST");
			sd.validate_the_placeid_mapped_to_the_using_something("Hindustan Times", "GetPlaceAPI");
		}
		
		
	}

}
