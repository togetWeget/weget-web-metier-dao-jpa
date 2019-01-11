package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AppRoleRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;

@Service
public class AppRoleMetierImpl implements IAppRoleMetier {
   
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Override
	public AppRoles creer(AppRoles entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return appRoleRepository.save(entity) ;
	}

	@Override
	public AppRoles modifier(AppRoles entity) throws InvalideTogetException {
		
		return appRoleRepository.save(entity);
	}

	@Override
	public List<AppRoles> findAll() {
		// TODO Auto-generated method stub
		return appRoleRepository.findAll();
	}

	@Override
	public AppRoles findById(Long id) {
		
		return appRoleRepository.getRoleByid(id);
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
		
		return appRoleRepository.findByNom(nom);
	}

	
}
