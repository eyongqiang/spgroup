package spgroup;

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
    public FriendResponse beFriend( @PathVariable String email) {
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
}
