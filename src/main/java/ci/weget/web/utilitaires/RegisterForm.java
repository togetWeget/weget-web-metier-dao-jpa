package ci.weget.web.utilitaires;

public class RegisterForm {

private String login;
private String password;
private String repasswrd;
public RegisterForm() {
	super();
	
}
public RegisterForm(String login, String password, String repasswrd) {
	super();
	this.login = login;
	this.password = password;
	this.repasswrd = repasswrd;
}
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRepasswrd() {
	return repasswrd;
}
public void setRepasswrd(String repasswrd) {
	this.repasswrd = repasswrd;
}

}
