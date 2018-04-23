package spgroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
		String[] emails = { "yongqiang.j.zhu@gmail.com", "common@example.com", "andy@example.com", "john@example.com" };
		for (String email : emails) {
			People tmp = new People();
			tmp.setEmail(email);
			peoples.put(email, tmp);
		}
	}

	public FriendResponse createFriend(FriendRequest friendReq) {
		log.debug("friendReq: {}", friendReq);
		String error="";
		FriendResponse res = new FriendResponse();
		// -1 last user already friend of others
		for (int i = 0; i < friendReq.getFriends().size() - 1; i++) {
			String main = friendReq.getFriends().get(i);
			People mainPeople = this.findPeople(main);
			if(mainPeople==null) {
				error = error+main+" - not found.";
			}
			// i+1 skip main him self
			for (int j = i + 1; j < friendReq.getFriends().size(); j++) {
				String other = friendReq.getFriends().get(j);
				if(!mainPeople.getFriends().contains(other)) {
					
					People otherPeople = this.findPeople(other);
					if (otherPeople == null) {
						error = error+other +" - not found.";
					} else {
						mainPeople.getFriends().add(other);
						otherPeople.getFriends().add(main);
					}
				}else {
					error = error+main+"&"+other+" are already friend.";
				}
			}
		}
		res.setMessage(error);
		if(error.isEmpty()) {
			res.setSuccess(true);
		}
		return res;
	}

	public People findPeople(String email) {
		return peoples.get(email);
	}
	
	public List<String> findCommonFriend(People user1,People user2) {
		
		List<String> result = new ArrayList<String>();
		HashSet<String> user1Index= new HashSet<String>(user1.getFriends());
		for(String friend:user2.getFriends()) {
			if(user1Index.contains(friend)) {
				result.add(friend);
			}
		}
		return result;
		
	}
}
