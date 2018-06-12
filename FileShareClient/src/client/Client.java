package client;

// Class used to easily pass client username and password

public class Client {

String username;
String pwd;

	public Client(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPwd() {
		return this.pwd;
	}
}
