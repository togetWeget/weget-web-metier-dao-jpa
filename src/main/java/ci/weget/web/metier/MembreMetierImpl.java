package ci.weget.web.metier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.CommandeRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.LigneCommandeRepo;
import ci.weget.web.dao.PaiementRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.RoleRepository;
import ci.weget.web.dao.UserRoleRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.LigneCommande;
import ci.weget.web.entites.Paiement;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.TypeStatut;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.Panier;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
public class MembreMetierImpl implements IMembreMetier {

	@Autowired
	private CommandeRepository commandeRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private LigneCommandeRepo ligneCommandeRepo;
	@Autowired
	private DetailBlocksRepository detailBlocksRepository;
	@Autowired
	private BlocksRepository blocksRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PaiementRepository paiementRepository;
	@Autowired
	private IDetailBlocksMetier detailBlocksMetier;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Personne findById(final Long id) {

		return personnesRepository.getPersonneByid(id);
	}

	@Override
	public Personne creer(Personne p) throws InvalideTogetException {
		if (!p.getPassword().equals(p.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		}
		Personne pers = null;

		try {
			pers = personnesRepository.findByLogin(p.getLogin());

		} catch (Exception e) {
			throw new InvalideTogetException("probleme de connexion");
		}
		if (pers != null) {
			throw new InvalideTogetException("ce login est deja utilise");
		}

		String hshPW = bCryptPasswordEncoder.encode(p.getPassword());
		String hshRPW = bCryptPasswordEncoder.encode(p.getRepassword());
		p.setPassword(hshPW);
		p.setRepassword(hshRPW);
		return personnesRepository.save(p);
	}

	@Override
	public Personne modifier(Personne modif) throws InvalideTogetException {
		Personne p = personnesRepository.getPersonneByid(modif.getId());

		if (p != null && p.getId() != modif.getId()) {

			throw new InvalideTogetException("cette personne est deja une autre personne");

		}

		return personnesRepository.save(p);
	}

	// rechercher des personnes par competence
	@Override
	public List<Personne> chercherPersonneParCompetence(String specialite) {

		return personnesRepository.chercherPersonneParSpecialite(specialite);
	}

	// la liste de tous les membres
	@Override
	public List<Personne> personneALL(String type) {
		List<Personne> pers = personnesRepository.findAll();

		List<Personne> typePersonnes = pers.stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());

		return typePersonnes;

	}
	// ramener les abonne a afficher sur la page d'accueil
		@Override
		public List<DetailBlock> membreAbonne(DetailBlock p) {
			List<DetailBlock> pers = detailBlocksRepository.findAll();

			List<DetailBlock> typePersonnes = pers.stream().filter(x -> p.getPersonne().isActived()).collect(Collectors.toList());

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
		List<AppRoles> roles = personnesRepository.getRoles(personne.getId());
		roles.add(appRole);

	}

	@Override
	public Commande enregistrerCommande(Panier p, Personne pers) {
		personnesRepository.save(pers);
		Commande cmd = new Commande();
		cmd.setDateCommande(LocalDateTime.now());
		cmd.setOrderLines(p.getItems());
		for (LigneCommande lc : p.getItems()) {
			ligneCommandeRepo.save(lc);
		}
		commandeRepository.save(cmd);
		return cmd;
	}

	@Override
	public List<Personne> chercherAbonneParId(Long id) {
		return personnesRepository.chercherAbonneParId(id);
	}

	@Override
	public Personne findByLogin(String login) {

		return personnesRepository.findByLogin(login);
	}

	@Override
	public List<Personne> findAll() {

		return personnesRepository.findAllMembres();
	}

	//////////// ajouter des personnes a des blocks ou des blocks a des
	//////////// personnes///////////
	@Override
	public void addPersonneToBlocks(String login, String libelle) throws InvalideTogetException {

		Personne personne = personnesRepository.findByLogin(login);
		Block block = blocksRepository.findByLibelle(libelle);
		DetailBlock db = new DetailBlock(personne, block);
		detailBlocksRepository.save(db);
		

	}

	// abonne est creer lorque la personne a paye le montant desirer
	// alors son statut est mis a Abonne qui est une enumeration
	@Override
	public Personne creerAbonne(String login, String libelle) throws InvalideTogetException {
		// recuperer le paiement du membre
		Personne p1 = personnesRepository.findByLogin(login);
		Paiement pay1 = paiementRepository.getPaiementParPersonne(p1.getId());
		// System.out.println(pay1.isPaye());
		// verifier si le champ isPaye est a true
		if (pay1.getMontant() == 9000) {
			// alors on declare le membre comme un abonne
			// on recupere son statut et on le met a abonne
			TypeStatut abonne = new TypeStatut("abonne", "");
			p1.setTypestatut(abonne);
			// on le lit a au block auquel il s'est abonne
			Block block = blocksRepository.findByLibelle(libelle);
			AppRoles role = roleRepository.findByNom("abonne");
			
			addRoleToUser(p1.getLogin(), role.getNom());

			addPersonneToBlocks(p1.getLogin(), block.getLibelle());
		}

		return p1;
	}
	// abonne est creer lorque la personne a paye le montant desirer
		// alors son statut est mis a Abonne qui est une enumeration
		@Override
		public Personne creerAbonneSpecial(String login, String libelle) throws InvalideTogetException {
			// recuperer le paiement du membre
			Personne p1 = personnesRepository.findByLogin(login);
			Paiement pay1 = paiementRepository.getPaiementParPersonne(p1.getId());
			// System.out.println(pay1.isPaye());
			// verifier si le champ isPaye est a true
			if (pay1.getMontant() == 10000) {
				// alors on declare le membre comme un abonne
				// on recupere son statut et on le met a abonne
				// p1.setTypeStatut(LibelleStatut.Abonnes);
				// on le lit a au block auquel il s'est abonne
				Block block = blocksRepository.findByLibelle(libelle);
				AppRoles role = roleRepository.findByNom("abonne");
				
				addRoleToUser(p1.getLogin(), role.getNom());

				addPersonneToBlocks(p1.getLogin(), block.getLibelle());
			}

			return p1;
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

	@Override
	public Personne findAbonneByLogin(String login) throws InvalideTogetException {
		Personne p1 = personnesRepository.findByLogin(login);
		if (p1.getTypestatut().getLibelle().equals("Abonne")) {
			return personnesRepository.findByLogin(login);
		} else {
			throw new InvalideTogetException("pas d'abonne");
		}

	}

	@Override
	public List<Personne> getAllAbonnes() {

		return personnesRepository.getAllAbonnes();
	}

	@Override
	public List<DetailBlock> lesAbonneParBlock(Long id) {

		return detailBlocksRepository.findAllAbonneParBloc(id);
	}
}
