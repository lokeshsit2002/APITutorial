package com.app.qa.resources;

import java.util.ArrayList;
import java.util.List;

import com.app.qa.pojo.testdata.AddPlace;
import com.app.qa.pojo.testdata.Location;

public class PayloadBuilds {
	
	public AddPlace addPlacePayload(String name, String language, String address) {
		AddPlace ap = new AddPlace();

		ap.setAccuracy(50);
		ap.setAddress(address);
		ap.setLanguage(language);
		// set location
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);
		//
		ap.setName(name);
		ap.setPhone_number("(+91) 983 893 3937");

		// set types
		List<String> tlist = new ArrayList<String>();
		tlist.add("shoe park");
		tlist.add("shop");
		ap.setTypes(tlist);
		//
		ap.setWebsite("http://google.com");
		
		return ap;

	}
	
	public String deletePayload(String placeId) {
		return "{\r\n" + 
				"	\"place_id\" : \""+placeId+"\"\r\n" + 
				"	\r\n" + 
				"}";
	}
	
	public static String deleteBookDetails(String Id) {
		String dBookDetails="{\r\n" + 
				"	\"ID\" : \""+Id+"\"\r\n" + 
				"	\r\n" + 
				"}";
		return dBookDetails;
	}

	

}
