package spgroup.model;
import java.util.List;

public class FriendRequest {
	@Override
	public String toString() {
		return "FriendRequest [friends=" + friends + "]";
	}

	private List<String> friends;

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
}
