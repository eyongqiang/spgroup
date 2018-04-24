package spgroup.model;
import java.util.List;

public class FriendRequest {
	private List<String> friends;
	private String requestor;
	private String sender;
	private String text;
	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getRequestor() {
		return requestor;
	}


	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	private String target;

	public List<String> getFriends() {
		return friends;
	}


	public String getTarget() {
		return target;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "FriendRequest [friends=" + friends + ", requestor=" + requestor + ", target=" + target + "]";
	}
}
