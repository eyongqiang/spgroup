package spgroup.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spgroup.model.FriendRequest;
import spgroup.model.FriendResponse;
import spgroup.model.People;

@Service
public class PeopleService {
	private static Logger log = LoggerFactory.getLogger(PeopleService.class);
	static HashMap<String, People> peoples;

	static {
		peoples = new HashMap<String, People>();
		String[] emails = { "yongqiang.j.zhu@gmail.com", "batman@wyn.com", "andy@example.com", "john@example.com" };
		for (String email : emails) {
			People tmp = new People();
			tmp.setEmail(email);
			peoples.put(email, tmp);
		}
	}

	public FriendResponse createFriend(FriendRequest friendReq) {
		log.debug("friendReq: {}", friendReq);
		FriendResponse res = new FriendResponse();
		// -1 last user already friend of others
		for (int i = 0; i < friendReq.getFriends().size() - 1; i++) {
			String main = friendReq.getFriends().get(i);
			People mainPeople = this.findPeople(main);
			System.out.println("mainPeople:" + mainPeople);
			if(mainPeople==null)
				return res;
			// i+1 skip main him self
			for (int j = i + 1; j < friendReq.getFriends().size(); j++) {
				String other = friendReq.getFriends().get(j);
				if(!mainPeople.getFriends().contains(other)) {
					System.out.println("email: " + other);
					People otherPeople = this.findPeople(other);
					if (otherPeople == null) {
						//default is res.setSuccess(false);
					} else {
						mainPeople.getFriends().add(other);
						otherPeople.getFriends().add(main);
						res.setSuccess(true);
					}
				}
			}
		}
		return res;
	}

	public People findPeople(String email) {
		return peoples.get(email);
	}
}
