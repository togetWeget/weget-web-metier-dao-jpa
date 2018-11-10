package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
@Transactional
public class MembreMetierImpl implements IMembreMetier {

	@Autowired
	private PersonnesRepository personnesRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/////// creer un utilisateur sur firebase///////////////////////////////////////
	public UserRecord createUser(Personne personne) throws FirebaseAuthException {

		CreateRequest request = new CreateRequest().setEmail(personne.getLogin())
				// .setEmailVerified(personne.getAdresse().getEmail())
				.setPassword(personne.getPassword());

		UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
		System.out.println("utilisateur creer avec success: " + userRecord.getUid());
		System.out.println("utilisateur creer avec success: "+ FirebaseAuth.getInstance().getUserAsync(userRecord.getUid()));

		return userRecord;

	}
	@Override
	public UserRecord updateUser(Personne personne) throws Exception {
		UpdateRequest  request = new UpdateRequest (personne.getLogin())
				.setEmail(personne.getLogin())
				// .setEmailVerified(personne.getAdresse().getEmail())
				.setPassword(personne.getPassword());
		UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);

		return userRecord;
	}

	@Override
	public Personne findById(final Long id) {

		return personnesRepository.getPersonneByid(id);
	}

	@Override
	public Personne creer(Personne p) throws InvalideTogetException {
		/*if (!p.getPassword().equals(p.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		}*/
		Personne pers = null;

		pers = personnesRepository.findByLogin(p.getLogin());
		if (pers != null)
			throw new InvalideTogetException("ce login est deja utilise");

		String hshPW = bCryptPasswordEncoder.encode(p.getPassword());
		String hshRPW = bCryptPasswordEncoder.encode(p.getRepassword());
		p.setPassword(hshPW);
		p.setRepassword(hshRPW);
		return personnesRepository.save(p);
	}

	@Override
	public Personne modifier(Personne modif) throws InvalideTogetException {

		if (modif != null) {
			Personne pers = personnesRepository.findByLogin(modif.getLogin());
			if (pers.getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else {
			throw new InvalideTogetException("modif est un objet null");
		}
		Personne pers1 = personnesRepository.findByLogin(modif.getLogin());
		String hshPW = pers1.getPassword();
		String hshRPW = pers1.getRepassword();
		modif.setPassword(hshPW);
		modif.setRepassword(hshRPW);
		return personnesRepository.save(modif);
	}

	// la liste de tous les membres
	@Override
	public List<Personne> personneALL(String type) {
		List<Personne> pers = personnesRepository.findAll();

		List<Personne> typePersonnes = pers.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());

		return typePersonnes;

	}

	///////// ajouter un role aun utilisateur
	///////// ////////////////////////////////////////////////
	@Override
	public void addRoleToUser(String login, String roleName) {
		Personne personne = personnesRepository.findByLogin(login);
		AppRoles appRole = roleRepository.findByNom(roleName);
		UserRoles userRole = new UserRoles(personne, appRole);
		userRoleRepository.save(userRole);
		/*List<AppRoles> roles = personnesRepository.getRoles(personne.getId());
		roles.add(appRole);*/

	}

	@Override
	public Commande enregistrerCommande(Panier p, Personne pers) {
		/*
		 * personnesRepository.save(pers); Commande cmd = new Commande();
		 * cmd.setDateCommande(LocalDateTime.now()); cmd.setOrderLines(p.getItems());
		 * for (Panier lc : p.getItems()) { ligneCommandeRepo.save(lc); }
		 * commandeRepository.save(cmd);
		 */
		return null;
	}

	@Override
	public Personne findByLogin(String login) {

		return personnesRepository.findByLogin(login);
	}

	@Override
	public List<Personne> findAll() {

		return personnesRepository.findAllMembres();
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

		return personnesRepository.existsById(id);
	}

	

}
