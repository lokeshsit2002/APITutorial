Feature: Validation Place APIs 

@AddPlace
Scenario Outline: Validate if Place is successfully added using AddPlaceAPI 
	Given Add Place Payload with <name>,<language>,<address> 
	When User calls "AddPlaceAPI" to "POST" http add request 
	Then The API call gets success with status code 200 
	And The "status" of response body is "OK" 
	And The "scope" of response body is "APP" 
	And Validate the PlaceId mapped to the <name> using "GetPlaceAPI" 
	
	Examples: 
		|name		|language	|address				|
		|TimesNow	|English	|Electronic City Phase1	|
		
@DeletePlace		
Scenario: Validate if Delete Place functionality is working 
	Given Delete Place Payload 
	When User calls "DeletePlaceAPI" to "POST" http add request 
	Then The API call gets success with status code 200 
	And The "status" of response body is "OK"