package ci.weget.web.metier;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
public class UserRoleMetierImpl implements IUserRoleMetier{

	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserRoles ajoutUserRole(Personne personne, AppRoles role) {
		
    return userRoleRepository.save(new UserRoles(personne, role));
	}
	@Override
	public UserRoles creer(UserRoles entity) throws InvalideTogetException {
		
		return null;
	}

	@Override
	public UserRoles modifier(UserRoles entity) throws InvalideTogetException {
		
		return null;
	}

	@Override
	public List<UserRoles> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoles findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<UserRoles> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
