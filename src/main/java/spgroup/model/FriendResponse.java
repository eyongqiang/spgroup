package spgroup.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class FriendResponse {
	private List<String> friends = new ArrayList<String>();
	private String message;
	private boolean success=false;

	public List<String> getFriends() {
		return friends;
	}
	public String getMessage() {
		return message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "FriendResponse [friends=" + friends + ", message=" + message + ", success=" + success + "]";
	}
	
	public Integer getCount() {
		int result = this.friends.size();
		if(result>0)
			return this.friends.size();
		else {
			return null;
		}
	}
}
