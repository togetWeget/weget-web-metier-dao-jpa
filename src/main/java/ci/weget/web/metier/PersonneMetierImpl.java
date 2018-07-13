package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.PaiementRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Paiement;
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
	private BlocksRepository blocksRepository;
	@Autowired
	private PaiementRepository paiementRepository;
	@Autowired
	private DetailBlocksRepository detailBlocksRepository;
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
//////////   enregistrer un role////////////////////////////////////////////////////////
	@Override
	public AppRoles saveRole(AppRoles role) {

		return roleRepository.save(role);
	}
   ///////// ajouter un role aun utilisateur ////////////////////////////////////////////////
	@Override
	public void addRoleToUser(String login, String roleName) {
		Personnes personne = personnesRepository.findByLogin(login);
		AppRoles appRole = roleRepository.findByNom(roleName);
		UserRoles userRole = new UserRoles(personne, appRole);
		userRoleRepository.save(userRole);
		List<AppRoles> roles = personnesRepository.getRoles(personne.getId());
		roles.add(appRole);

	}
	//////////// ajouter des personnes a des blocks ou des blocks a des persnnes///////////
	@Override
	public void addPersonneToBlocks(String login, String libelle) {
		
		Personnes personne = personnesRepository.findByLogin(login);
		
		Blocks block = blocksRepository.findByLibelle(libelle);
		Paiement paie = paiementRepository.getPaiementParBlockLibelle(libelle);
		if (paie.paye=true) {
			DetailBlocks db = new DetailBlocks(block, personne);
			detailBlocksRepository.save(db);
			List<Personnes> personnes=personnesRepository.getPersonneParBlockLibelle(block.getLibelle());
			personnes.add(personne);
		}else {
			System.out.println("vous devez paye d'abord");
		}
		//paie.setBlock(block);
	//	paiementRepository.save(paie);
		
		
	    }
	
	///////////////// enregistrer un userRole//////////////////////////////////////////////
	@Override
	public UserRoles saveUserRole(UserRoles ur) {
		
		return userRoleRepository.save(ur);
	}
	
	//////////////////ramener une personnes par son login //////////////////////////////////
   @Override
	public Personnes findPersonnesByLogin(String login) {
		return personnesRepository.findByLogin(login);
	}
  //////////////// recuperer une personne par son id///////////////////////////////////////
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
	public Personnes findByNom(String nom) {
	
		return null;
	}

	@Override
	public List<Personnes> findAllPersonnesParMc(String type, String mc) {
		
		return null;
	}

	@Override
	public List<Personnes> findAllAdministrateurs() {
		
		return null;
	}

	@Override
	public List<Personnes> findAllMembres() {
		
		return null;
	}

	@Override
	public List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet) {
		
		return null;
	}

	@Override
	public List<Personnes> findAll() {
		
		return null;
	}
	
	
}
