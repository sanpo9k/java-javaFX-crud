package utils;

public class ListCurrent {
	private String UserID;
	private String usernameUser;
	/**
	 * @param userID
	 * @param usernameUser
	 */
	public ListCurrent(String userID, String usernameUser) {
		UserID = userID;
		this.usernameUser = usernameUser;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUsernameUser() {
		return usernameUser;
	}
	public void setUsernameUser(String usernameUser) {
		this.usernameUser = usernameUser;
	}
	
	
}
