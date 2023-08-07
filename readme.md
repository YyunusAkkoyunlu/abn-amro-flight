# Flight API

This project contains rest api application created to searching and retrieving flight information based on various criteria.


# About The Project
This Project is developed to show flights search results with criteria. This contains an api's to show and perform below actions:
* Search flights with criterias


# Build With
This project has build up with following frameworks and tools/technology used:
* Spring Boot
* Restfull Service
* Java


# Getting Started
## Prerequisites
	Following are the pre-requisites:
	
	* Intellij IDE
	* Postman

## Installation

	1. Import project
	2. Maven present in project
    3. Enable lombok
	3. Build project and Run as java application 
	4. Use rest service in postman to perform actions

## Example
    1. We can access flight details via - POST http://localhost:8080/api/flight/getFlightsWithParameters
```
{
    "origin" : "AMS",
    "sortedBy" : "price"
}

    2. We can add different fields as criteria to our object like : "destination", "price" etc.

    3. If we want to add/edit flight details, we can modify application.yml file and restart application
```