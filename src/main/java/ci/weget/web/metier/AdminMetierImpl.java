package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AppRoleRepository;
import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Administrateur;
import ci.weget.web.entites.Adresse;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
public class AdminMetierImpl implements IAdminMetier {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private AbonnementRepository detailBlocksRepository;
	/*
	 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	 */

	////////////////////////////////////////////////////////////////////////////
	/////// code mis en place pour enregistrer une personne /////////////////////
	@Override
	public Personne creer(Personne entity) throws InvalideTogetException {
		if (!entity.getPassword().equals(entity.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		}
		Administrateur pers = null;
		pers = personnesRepository.findAdminByLogin(entity.getLogin());
		if (pers != null)
			throw new InvalideTogetException("ce login est deja utilise");

		Adresse adresse = new Adresse();
		adresse.setLatitude(0);
		adresse.setLongitude(0);
		entity.setAdresse(adresse);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setRepassword(passwordEncoder.encode(entity.getRepassword()));
		 
		return personnesRepository.save(entity);

	}

	///////////////////////////////////////////////////////////////////////////////////////
	//////////////// code metier pour modifier une
	/////////////////////////////////////////////////////////////////////////////////////// personne////////////////////////////
	@Override
	public Personne modifier(Personne entity) throws InvalideTogetException {
		Personne p = personnesRepository.findById(entity.getId()).get();

		if (p != null && p.getId() != entity.getId()) {

			throw new InvalideTogetException("cette personne est deja une autre personne");

		}

		return personnesRepository.save(entity);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////// voir la liste de toutes les personnes en fonction des
	//////////////////////////////////////////////////////////////////////////////////////////// types//////////////////////
	@Override
	public List<Personne> personneALL(String type) {
		List<Personne> pers = personnesRepository.findAll();

		List<Personne> typePersonnes = pers.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());

		return typePersonnes;

	}

	////////// enregistrer un
	////////// role////////////////////////////////////////////////////////
	/*@Override
	public AppRoles saveRole(AppRoles role) {

		return appRoleRepository.save(role);
	}
*/
	///////// ajouter un role aun utilisateur
	///////// ////////////////////////////////////////////////
	/*@Override
	public void addRoleToUser(String login, String roleName) {
		Personne personne = personnesRepository.findByMembreLogin(login);
		AppRoles appRole = appRoleRepository.findByNom(roleName);
		UserRoles userRole = new UserRoles(personne, appRole);
		userRoleRepository.save(userRole);
		List<AppRoles> roles = personnesRepository.getRoles(personne.getId());
		roles.add(appRole);

	}*/

	///////////////// enregistrer un
	///////////////// userRole//////////////////////////////////////////////
	/*@Override
	public UserRoles saveUserRole(UserRoles ur) {

		return userRoleRepository.save(ur);
	}
*/
	////////////////// ramener une personnes par son login
	////////////////// //////////////////////////////////
	@Override
	public Personne findPersonnesByLogin(String login) {
		return personnesRepository.findByMembreLogin(login);
	}

	//////////////// recuperer une personne par son
	//////////////// id///////////////////////////////////////
	@Override
	public Personne findById(Long id) {

		return personnesRepository.getPersonneByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		personnesRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Personne> entites) {
		personnesRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		personnesRepository.existsById(id);
		return true;
	}

	/*@Override
	public List<AppRoles> getRoles(long id) {
		return personnesRepository.getRoles(id);
	}

	@Override
	public List<AppRoles> getRoles(String login, String password) {

		return personnesRepository.getRoles(login, password);
	}
*/
	@Override
	public Personne findByNom(String nom) {

		return personnesRepository.findByNom(nom);
	}

	@Override
	public List<Personne> findAllPersonnesParMc(String type, String mc) {

		return null;
	}

	@Override
	public List<Personne> findAllAdministrateurs() {

		return null;
	}

	@Override
	public List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet) {

		return null;
	}

	@Override
	public List<Personne> findAll() {

		return null;
	}

/*	// la liste des roles d'une personne
	@Override
	public List<UserRoles> roleParPersonneId(Long id) {

		return userRoleRepository.roleParPersonneId(id);
	}
*/
}
