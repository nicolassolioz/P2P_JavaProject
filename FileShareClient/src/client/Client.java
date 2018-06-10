package client;

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
