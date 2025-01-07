

import static io.restassured.RestAssured.given;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertNotNull;

public class APIAutomation 
{
	@Test
	public void testAPI()
	{	
		Response response = given().when().get("https://api.coindesk.com/v1/bpi/currentprice.json");
		
		String responseStr = response.then().extract().asString();
		
		JSONObject jsonObject = new JSONObject(responseStr);
		
		JSONObject bpi = jsonObject.getJSONObject("bpi");
		
		//Validate that USD, GBP and EUR are available under bpi
		String usdValue= bpi.getJSONObject("USD").toString();
		String gbpValue= bpi.getJSONObject("GBP").toString();
		String eurValue= bpi.getJSONObject("EUR").toString();
		 
		assertNotNull(usdValue, "USD json object is not null");
		assertNotNull(gbpValue, "GBP json object is not null");
		assertNotNull(eurValue, "EUR json object is not null");
		 
		//Validate GBP description equals "British Pound Sterling"
		response.then().body("bpi.GBP.description", equalTo("British Pound Sterling"));
	}
}
