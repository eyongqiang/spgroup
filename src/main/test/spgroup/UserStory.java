package spgroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import spgroup.model.FriendRequest;
import spgroup.model.FriendResponse;
/**
 * @author yongqiang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserStory {
	@Autowired
	private TestRestTemplate template;
	@Test
	public void exampleTest() {
		Gson gson = new Gson();
	    String json = "{\"friends\":[\"andy@example.com\",\"john@example.com\"]}";
	    FriendRequest friendReq= gson.fromJson(json, FriendRequest.class);
	    System.out.println("friendReq:"+friendReq);
	    FriendResponse result = this.template.postForObject("/api/people/befriend", friendReq, FriendResponse.class);
	    System.out.println("response: "+result);
	}
}
