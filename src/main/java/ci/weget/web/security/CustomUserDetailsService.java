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
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    PersonnesRepository personnesRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
    	Personne user = personnesRepository.findPersonneByLogin(login);
		if (user==null) {
			throw new UsernameNotFoundException(login);
		}
		return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Personne user = personnesRepository.getPersonneByid(id);
       
        if (user==null) {
			throw new UsernameNotFoundException("not found"+id);
		}
		
        return UserPrincipal.create(user);
    }
}