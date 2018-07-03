package ci.weget.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ci.weget.web.entites.Personnes;
import ci.weget.web.metier.IPersonneMetier;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
	@Autowired
	private IPersonneMetier personneMetier;
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Personnes user = personneMetier.findPersonnesByLogin(login);
		if (user==null) {
			throw new UsernameNotFoundException(login);
		}
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<AppRoles> roles = personneMetier.getRoles(user.getId());
		roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.getNom()));
		});
		return new User(user.getLogin(),user.getPassword(),authorities);
	}

}
