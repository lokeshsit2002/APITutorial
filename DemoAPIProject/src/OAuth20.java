import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth20 {
	
	
	@Test
	public void oauth20Example() {
		
		//Get code Plz generate the url value using postman get code url in InCognito mode
		String url = "https://rahulshettyacademy.com/getCourse.php?state=yogesh123&code=4%2F0AEiJps6vw_itZOFwdvFCFT3aKuELD8x_ENurckRLYO8AXUdrDFcCVbe7vS_lmPLMZ45frFVWSzbgDRCaNxMnz8&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
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
		
		//Get the output of rahul shetty url using access token
		String response=given()
				.queryParam("access_token", token)
				.contentType("application/json").expect().defaultParser(Parser.JSON)
		        .when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println("Response : "+response);
	}

}
