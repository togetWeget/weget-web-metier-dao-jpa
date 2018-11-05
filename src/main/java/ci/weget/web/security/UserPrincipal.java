package ci.weget.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.Personne;
import ci.weget.web.metier.IAdminMetier;

public class UserPrincipal extends AbstractEntity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private static IAdminMetier adminMetier;
	
    private String login;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String repassword;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String login, String password,String repassword,
			Collection<? extends GrantedAuthority> authorities) {
		this.login = login;
		this.password = password;
		this.repassword = repassword;
        this.authorities = authorities;
	}

	public UserPrincipal(String login, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.login = login;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(Personne user) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<AppRoles> roles = adminMetier.getRoles(user.getId());
		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getNom()));
		});
		return new UserPrincipal(user.getLogin(), user.getPassword(), authorities);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((repassword == null) ? 0 : repassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPrincipal other = (UserPrincipal) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (repassword == null) {
			if (other.repassword != null)
				return false;
		} else if (!repassword.equals(other.repassword))
			return false;
		return true;
	}

	
}
