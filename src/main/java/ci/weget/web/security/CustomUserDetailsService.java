package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Personne;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    PersonnesRepository personnesRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Personne personne = personnesRepository.findByLogin(login);
        		if (personne==null) {
        			new UsernameNotFoundException("User not found with username or email : " + login);
        		}

        return UserPrincipal.create(personne);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Personne personne = personnesRepository.getPersonneByid(id);
        if (personne==null) {
        	new UsernameNotFoundException("User not found with id : " + id);
		}
        return UserPrincipal.create(personne);
    }
}
