package spgroup;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import spgroup.model.FriendRequest;
import spgroup.service.PeopleService;
/**
 * @author yongqiang
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = SocialJsonController.class, secure = false)
public class UserStory {
	@Autowired private MockMvc mockMvc;
	@MockBean private PeopleService peopleService;
	@Test
	public void exampleTest() {
		Gson gson = new Gson();
	    String json = "{\"friends\":[\"andy@example.com\",\"john@example.com\"]}";
	    FriendRequest friendReq= gson.fromJson(json, FriendRequest.class);
	    System.out.println("friendReq:"+friendReq);
		RequestBuilder requestBuilder = 
		MockMvcRequestBuilders.post("/api/people/befriend")
			.accept(MediaType.APPLICATION_JSON).content(json);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder)
					.andExpect(status().isOk())
		            .andExpect(content().json("{\"success\":true}"))
		            .andReturn();
;
			System.out.println("Response: "+result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
