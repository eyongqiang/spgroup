package spgroup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spgroup.model.FriendRequest;
import spgroup.model.FriendResponse;
import spgroup.model.People;
import spgroup.service.PeopleService;

@RestController
@RequestMapping("/api")
public class SocialJsonController {
	@Autowired
	PeopleService peopleService;
	@RequestMapping(value="/people/befriend",method=RequestMethod.POST)
    public FriendResponse beFriend( @RequestBody FriendRequest friendReq) {
		System.out.println("friendReq:"+friendReq);
        return peopleService.createFriend(friendReq);
    }
	@RequestMapping(value="/people/{email}",method=RequestMethod.GET)
    public FriendResponse listFriend( @PathVariable String email) {
		FriendResponse res = new FriendResponse();
		People p = peopleService.findPeople(email);
		if(p!=null) {
			res.setFriends(p.getFriends());
			res.setSuccess(true);
		}else {
			res.setMessage(email+" is not found.");
		}
        return res;
    }
	@RequestMapping(value="/people/common/friend/{email1}/{email2}",method=RequestMethod.GET)
    public FriendResponse commonFriend( @PathVariable String email1,@PathVariable String email2) {
		FriendResponse res = new FriendResponse();
		People user1 = peopleService.findPeople(email1);
		People user2 = peopleService.findPeople(email2);
		List<String> friends = peopleService.findCommonFriend(user1,user2);
		if(friends!=null) {
			res.setFriends(friends);
			res.setSuccess(true);
		}else {
			res.setMessage("no common friend or email not found.");
			res.setSuccess(true);
		}
        return res;
    }
	@RequestMapping(value="/subscribe",method=RequestMethod.POST)
    public FriendResponse subscribe(@RequestBody FriendRequest friendReq) {
		return this.peopleService.addSubscriber(friendReq);
	}
	@RequestMapping(value="/block/{requstor}/{target}",method=RequestMethod.GET)
    public FriendResponse block( @PathVariable String requstor,@PathVariable String target) {
		return null;
	}
	@RequestMapping(value="/message/{requstor}/{target}",method=RequestMethod.POST)
    public FriendResponse sendMessage( @PathVariable String sender,@PathVariable String text) {
		return null;
	}
}
