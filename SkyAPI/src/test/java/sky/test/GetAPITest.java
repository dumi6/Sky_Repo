package sky.test;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import restApi.TestBase;
import sky.client.RestClient;

public class GetAPITest extends TestBase{

	TestBase testBase; 
	String baseUrl;
	String resource;
	String url;
	RestClient restClient;

	@Before
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase(); 
		url = prop.getProperty("Url");	
	}
	
	@Test
	public void getSingleArticleTest() throws ClientProtocolException, IOException{
		
		String id = "/2";
		
		restClient = new RestClient();
		CloseableHttpResponse response = restClient.getArticles(url + id);
		
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("status Code => "+ statusCode);
		
		Assert.assertEquals(statusCode, 200);
							
		String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseBody);
		JSONObject resBody = null;
		
		try {
		     resBody = new JSONObject(responseBody);
		}catch (JSONException err){
		System.out.println(err.toString());
		}
			
		Assert.assertEquals(resBody.get("title"), "title 2");
		
	}	
	
	@Test
	public void getAllArticlesTest() throws ClientProtocolException, IOException{
		
		restClient = new RestClient();
		CloseableHttpResponse response = restClient.getArticles(url);
		
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("status Code => "+ statusCode);
		
		Assert.assertEquals(statusCode, 200);
							
		String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseBody);
		
		Assert.assertThat(responseBody, containsString("title 1"));
		
	}	
}
