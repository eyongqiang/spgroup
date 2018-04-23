package spgroup.model;

import java.util.ArrayList;
import java.util.List;

public class People {
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	private String email;
	@Override
	public String toString() {
		return "People [email=" + email + ", friends=" + friends + "]";
	}
	private List<String> friends = new ArrayList<String>();
}
