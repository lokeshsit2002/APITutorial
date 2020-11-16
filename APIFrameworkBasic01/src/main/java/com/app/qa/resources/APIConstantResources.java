package com.app.qa.resources;

public enum APIConstantResources {
	
	AddPlaceAPI("maps/api/place/add/json"),
	GetPlaceAPI("maps/api/place/get/json"),
	DeletePlaceAPI("maps/api/place/delete/json");
	
	private String resources;

	APIConstantResources(String resources){
		this.resources= resources;
	}

	public String getAPIConstantResources() {
		return resources;
	}
}
