package spgroup;

import static org.junit.Assert.assertEquals;

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
	public void story1() {
		String json = "{\"friends\":[\"andy@example.com\",\"john@example.com\"]}";
		Gson gson = new Gson();
		FriendRequest friendReq = gson.fromJson(json, FriendRequest.class);
		System.out.println("friendReq:" + friendReq);
		String jsonResponse = this.template.postForObject("/api/people/befriend", friendReq, String.class);
		assertEquals("return must be true", "{\"success\":true}", jsonResponse);
	}

	@Test
	public void story2() {
		String jsonResponse = this.template.getForObject("/api/people/john@example.com", String.class);
		assertEquals("return must be true", "{\"friends\":[\"andy@example.com\"],\"success\":true,\"count\":1}",
				jsonResponse);
	}

	@Test
	public void story3() {
		String json = "{\"friends\":[\"andy@example.com\",\"common@example.com\"]}";
		Gson gson = new Gson();
		FriendRequest friendReq = gson.fromJson(json, FriendRequest.class);
		String jsonResponse = this.template.postForObject("/api/people/befriend", friendReq, String.class);
		System.out.println("jsonResponse:" + jsonResponse);
		json = "{\"friends\":[\"john@example.com\",\"common@example.com\"]}";
		gson = new Gson();
		friendReq = gson.fromJson(json, FriendRequest.class);
		jsonResponse = this.template.postForObject("/api/people/befriend", friendReq, String.class);
		System.out.println("jsonResponse:" + jsonResponse);

		jsonResponse = this.template.getForObject("/api/people/common/friend/john@example.com/andy@example.com",
				String.class);
		assertEquals("return must be true", "{\"friends\":[\"common@example.com\"],\"success\":true,\"count\":1}",
				jsonResponse);
	}

}
