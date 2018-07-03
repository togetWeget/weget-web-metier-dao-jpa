package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
@Transactional
public class PersonneMetierImpl implements IPersonneMetier {

	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	////////////////////////////////////////////////////////////////////////////
	/////// code mis en place pour enregistrer une personne /////////////////////
	@Override
	public Personnes creer(Personnes entity) throws InvalideTogetException {
		if(!entity.getPassword().equals(entity.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		} 
		Personnes pers = null;
		try {
			pers = personnesRepository.findByLogin(entity.getLogin());
		} catch (Exception e) {
			throw new InvalideTogetException("probleme de connexion");
		}
		if (pers != null)
			throw new InvalideTogetException("ce login  existe deja");

        String hshPW = bCryptPasswordEncoder.encode(entity.getPassword());
        String hshRPW = bCryptPasswordEncoder.encode(entity.getRepassword());
		entity.setPassword(hshPW);
		entity.setRepassword(hshRPW);
		return personnesRepository.save(entity);

	}
///////////////////////////////////////////////////////////////////////////////////////
	////////////////code metier pour modifier une personne////////////////////////////
	@Override
	public Personnes modifier(Personnes entity) throws InvalideTogetException {
		Personnes p = personnesRepository.getOne(entity.getId());

		if (p != null && p.getId() != entity.getId()) {

			throw new InvalideTogetException("Cette personne existe déjà.");

		}
		return personnesRepository.save(entity);
	}
////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////voir la liste de toutes les personnes en fonction des types//////////////////////
	@Override
	public List<Personnes> personneALL(String type) {
		List<Personnes> pers = personnesRepository.findAll();

		List<Personnes> typePersonnes = pers.stream().filter(p -> p.getType().equals(type))
				.collect(Collectors.toList());

		return typePersonnes;

	}

	@Override
	public AppRoles saveRole(AppRoles role) {

		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String login, String roleName) {
		Personnes personne = personnesRepository.findByLogin(login);
		AppRoles appRole = roleRepository.findByNom(roleName);
		UserRoles userRole = new UserRoles(personne, appRole);
		userRoleRepository.save(userRole);
		List<AppRoles> roles = personnesRepository.getRoles(personne.getId());
		roles.add(appRole);

	}
	@Override
	public UserRoles saveUserRole(UserRoles ur) {
		
		return userRoleRepository.save(ur);
	}

	@Override
	public Personnes findPersonnesByLogin(String login) {
		return personnesRepository.findByLogin(login);
	}

	@Override
	public Personnes findById(Long id) {

		return personnesRepository.getOne(id);
	}

	@Override
	public boolean supprimer(Long id) {
		personnesRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Personnes> entites) {
		personnesRepository.deleteAll(entites);
		;
		return true;
	}

	@Override
	public boolean existe(Long id) {
		personnesRepository.existsById(id);
		return true;
	}

	@Override
	public List<AppRoles> getRoles(long id) {
		return personnesRepository.getRoles(id);
	}

	@Override
	public List<AppRoles> getRoles(String login, String password) {

		return personnesRepository.getRoles(login, password);
	}

	@Override
	public Personnes findByLogin(String login) {

		return personnesRepository.findByLogin(login);
	}

	@Override
	public List<Personnes> findByType(String type) {

		return null;
	}

	@Override
	public Personnes findByNom(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personnes> findAllPersonnesParMc(String type, String mc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personnes> findAllAdministrateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personnes> findAllMembres() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personnes> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
