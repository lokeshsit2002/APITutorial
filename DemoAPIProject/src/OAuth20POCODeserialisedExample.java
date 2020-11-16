import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import POJO.API;
import POJO.GetCourses;
import POJO.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth20POCODeserialisedExample {
	
	
	@Test
	public void oauth20Example() {
		
		String[] expectedWebItems= {"Selenium Webdriver Java","Cypress","Protractor"};
		
		//Get code Plz generate the url value using postman get code url in InCognito mode
		String url = "https://rahulshettyacademy.com/getCourse.php?state=yogesh123&code=4%2F0AEw9VN-2iCwMHW-DIT6ykuFrMX7SRjDPmnZuQ8bqoVNgDa66qzlX0gxr0y_gtF-EKHQF5yuB_n1-KzlddTSuvQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String partialCode= url.split("code=")[1];
		String code = partialCode.split("&scope=")[0];
		
		System.out.println("Code : "+code);
		
		//Get the access token
		String response_token= given().log().all()
				.urlEncodingEnabled(false)
		 .queryParam("code", code)
		 .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		 .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		 .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		 .queryParam("grant_type", "authorization_code")
		 .when().log().all()
		 .post("https://www.googleapis.com/oauth2/v4/token")
		 .asString();
		 
		JsonPath  jstok = new JsonPath(response_token);
		
		String token = jstok.getString("access_token");
		
		System.out.println("Access Token : "+token);
		
		//Get the output of rahul shetty url using access token VIA POCO
		GetCourses response=given()
				.queryParam("access_token", token)
				.expect().defaultParser(Parser.JSON)
		        .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
		
		System.out.println("Instructor : "+response.getInstructor());
		System.out.println("LinkedIn : "+response.getLinkedIn());
		
		// get the price of api coursetitle SoapUI Webservices testing
		
		List<API> apiList=response.getCourses().getApi();
		
		for(API apiitem:apiList) {
			if(apiitem.getCourseTitle().contains("SoapUI Webservices testing")){
				
				System.out.println("Price of SoapUI : "+apiitem.getPrice());
			}
		}
		
		ArrayList<String> actualWebItemList = new ArrayList<String>();
	   List<WebAutomation> webAutoList =response.getCourses().getWebAutomation();
	   
	   for(WebAutomation webitem:webAutoList) {
		   System.out.println("Web Course Title : "+webitem.getCourseTitle());
		   actualWebItemList.add(webitem.getCourseTitle());
	   }
	   
	 //change expectedwebitems as ArrayList and then compare easily
		List<String> expectedWebItemList = Arrays.asList(expectedWebItems);
		
		Assert.assertTrue(actualWebItemList.equals(expectedWebItemList));
	}

	
	
}
