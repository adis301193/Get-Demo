import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.reUsableMethods;

public class Basics {
@Test(dataProvider="Names&Number")
public void AddPlace(String name, String phone_number)
{
	 
		// TODO Auto-generated method stub

		// Add place API
		// restassured 3 methods/Principles
		// given:- all inputs
		// when:- Method + Resource
		// Then:- Validation

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//Add place 
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace(name,phone_number)).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);

		
		//Update place
		String newAddress = "Chandrapur";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "    \"place_id\": \"" + place_id + "\",\r\n" + "    \"address\": \"" + newAddress
						+ "\",\r\n" + "    \"key\": \"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		//Get Place

		String getplaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();
		
		JsonPath js1 = reUsableMethods.rawTojson(getplaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);

		Assert.assertEquals(actualAddress, newAddress);
		
		
		

		// Add place api-->Update Place with new address api-->Get place
		/// how to retreive value which are stored in json

	}
	
	@DataProvider(name="Names&Number")
	public  Object[][] getData()
	{
		return new Object[][]{{"Aditya","8888304875"},{"Apoorva","9834369895"},{"Seema","9370343322"}};
	}
}
