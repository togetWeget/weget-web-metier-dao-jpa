package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.RoleRepository;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;

@Service
public class AppRoleMetierImpl implements IAppRoleMetier{
   
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public AppRoles creer(AppRoles entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return roleRepository.save(entity) ;
	}

	@Override
	public AppRoles modifier(AppRoles entity) throws InvalideTogetException {
		
		return roleRepository.save(entity);
	}

	@Override
	public List<AppRoles> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public AppRoles findById(Long id) {
		
		return roleRepository.getRoleByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<AppRoles> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AppRoles findRoleByNom(String nom) {
		
		return roleRepository.findByNom(nom);
	}

	
}
