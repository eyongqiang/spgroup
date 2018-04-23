package spgroup.model;

public class FriendResponse {
	@Override
	public String toString() {
		return "FriendResponse [success=" + success + ", message=" + message + "]";
	}

	private boolean success=false;
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
