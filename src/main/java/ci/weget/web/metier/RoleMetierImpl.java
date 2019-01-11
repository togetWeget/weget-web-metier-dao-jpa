package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.RoleRepository;
import ci.weget.web.entites.Role;
import ci.weget.web.entites.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;

@Service
public class RoleMetierImpl implements IRoleMetier{
   
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role creer(Role entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return roleRepository.save(entity);
	}

	@Override
	public Role modifier(Role entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return roleRepository.save(entity);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Role> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role getUserRoleByNom(RoleName name) {
		// TODO Auto-generated method stub
		return roleRepository.getUserRoleParName(RoleName.ROLE_MEMBRE);
	}
	
}
