package com.app.qa.resources;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {
	public static RequestSpecification reqSpecBdr;
	public static ResponseSpecification responseSpecBdr;
	
	public RequestSpecification requestSpecification() throws IOException {
		
		//To make sure logging.txt is not overrriden if the scenario runs multiple times with different data
		if(reqSpecBdr==null){
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			
			reqSpecBdr = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL"))
					.addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
					
			return reqSpecBdr;
		}
		
		else {
			return reqSpecBdr;
		}
			
		
		
	}
	
	public ResponseSpecification responseSpecification() {
		responseSpecBdr = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		return responseSpecBdr;
	}

	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\lsingh38\\eclipse-workspace\\APIFrameworkBasic01\\src\\main\\java\\com\\app\\qa\\resources\\global.properties");
	    prop.load(fis);
	    return prop.getProperty(key);
	    
	}
	
	public String getValueUsingKeyAndJsonPath(Response response,String key) {
		
       String respString = response.asString();
		
		System.out.println(respString);
		
		JsonPath jsp = new JsonPath(respString);
		
		return jsp.get(key).toString();
		
	}
}
