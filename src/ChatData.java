/*
 * Ohjelmointi 3 - Preassignment
 * Tehnyt: Santeri Kottari
 */

public class ChatData {

	private String username;
	private String message;
	private String timestamp;

	public ChatData(final String username, final String message, final String timestamp) {
		this.username = username;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

}
