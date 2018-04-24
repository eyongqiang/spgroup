package spgroup.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import spgroup.model.FriendRequest;
import spgroup.model.FriendResponse;
import spgroup.model.People;

@Service
public class PeopleService {
	private static Logger log = LoggerFactory.getLogger(PeopleService.class);
	//TODO: it can be used cache in future
	protected static HashMap<String, People> peoples;
	public static void displayCache(){
		Iterator it = PeopleService.peoples.entrySet().iterator();
		System.out.println("============================\\/");
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + ((People)pair.getValue()));
	    }
	    System.out.println("============================/\\");
	}
	static {
		peoples = new HashMap<String, People>();
		String[] emails = { "yongqiang.j.zhu@gmail.com", "common@example.com", "andy@example.com", "john@example.com","lisa@example.com","kate@example.com" };
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
						if(!otherPeople.getBlocker().contains(mainPeople.getEmail())
								&&
								!mainPeople.getBlocker().contains(otherPeople.getEmail())
								) {
							mainPeople.getFriends().add(other);
							otherPeople.getFriends().add(main);
							
						}else {
							error= error+main+"&"+other+" are denied to be friend.";
						}
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

	public FriendResponse addSubscriber(String requstor, String target) {
		FriendResponse res = new FriendResponse();
		People req = this.findPeople(requstor);
		People sub = this.findPeople(target);
		if(req!=null&&sub!=null) {
			req.getSubscriber().add(target);
			res.setSuccess(true);
		}else {
			res.setMessage("Either requestor or target not exist");
		}
		return res;
	}

	public FriendResponse addSubscriber(FriendRequest friendReq) {
		FriendResponse res = new FriendResponse();
		People target = this.findPeople(friendReq.getTarget());
		String requstor = friendReq.getRequestor();
		if(target!=null && this.findPeople(requstor)!=null) {
			target.getSubscriber().add(requstor);
			res.setSuccess(true);
		}else {
			res.setMessage("Either requstor or target are not exist");
		}
		return res;
	}

	public FriendResponse block(FriendRequest friendReq) {
		FriendResponse res = new FriendResponse();
		People target = this.findPeople(friendReq.getTarget());
		People requstor = this.findPeople(friendReq.getRequestor());
		if(target!=null && requstor!=null) {
			requstor.getBlocker().add(target.getEmail());
			res.setSuccess(true);
		}else {
			res.setMessage("Either requstor or target are not exist");
		}
		return res;
	}

	public FriendResponse sendMessage(FriendRequest friendReq) {
		FriendResponse res = new FriendResponse();
		String text = friendReq.getText();
		if(friendReq.getSender()!=null && !friendReq.getSender().isEmpty()
				&& text!=null && !text.isEmpty()) {
			People sender = this.findPeople(friendReq.getSender());
			if(sender!=null) {
				List<String> recipients = new ArrayList<String>();
				//get all friend
				recipients.addAll(sender.getFriends());
				//get all subscriber
				recipients.addAll(sender.getSubscriber());
				//if above choice to block sender
				//TODO: when use database, change below
				List<String> whomBlock = new ArrayList<String>();
				for(String recipient:recipients) {
					People p = this.findPeople(recipient);
					System.out.println("recipient-->"+recipient+" is blocked "+sender.getEmail());
					if(p.getBlocker().contains(sender.getEmail())) {
						whomBlock.add(recipient);
					}
					
				}
				//get mention
				Matcher mention = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
				while (mention.find()) {
					recipients.add(mention.group());
			    }
				//check if recipient are listed in blocked
				recipients.removeAll(sender.getBlocker());
				recipients.removeAll(whomBlock);
				res.setRecipients(recipients);
				res.setSuccess(true);
			}else {
				res.setMessage("Sender is not found.");
			}
		}else {
			res.setMessage("Either Sender or Text are not given.");
		}
		
		return res;
	}
}
