package spgroup.model;

import java.util.ArrayList;
import java.util.List;

public class People {
	private String email;
	private List<String> friends = new ArrayList<String>();
	private List<String> subscriber = new ArrayList<String>();
	private List<String> blocker = new ArrayList<String>();

	public List<String> getBlocker() {
		return blocker;
	}
	public void setBlocker(List<String> blocker) {
		this.blocker = blocker;
	}
	public String getEmail() {
		return email;
	}
	public List<String> getFriends() {
		return friends;
	}
	public List<String> getSubscriber() {
		return subscriber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public void setSubscriber(List<String> subscriber) {
		this.subscriber = subscriber;
	}
	@Override
	public String toString() {
		return "People [email=" + email + ", friends=" + friends + ", subscriber=" + subscriber + ", blocker=" + blocker
				+ "]";
	}
	
}
