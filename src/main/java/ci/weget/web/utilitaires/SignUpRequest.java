package ci.weget.web.utilitaires;
import javax.validation.constraints.*;
public class SignUpRequest {
	@NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String repassword;

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

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

    
}
