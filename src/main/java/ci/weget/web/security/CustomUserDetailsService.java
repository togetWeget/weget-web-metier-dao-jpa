package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    PersonnesRepository personnesRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        // recuperer une personne a partir de son login
        Membre user = personnesRepository.findByLogin(login);
        		if (user==null) {
        			new UsernameNotFoundException("User not found with login: " + login);
        		}

        return UserPrincipal.create(user);
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
