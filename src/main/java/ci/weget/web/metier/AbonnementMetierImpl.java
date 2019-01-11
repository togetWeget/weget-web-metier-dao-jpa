package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class AbonnementMetierImpl implements IAbonnementMetier {

	@Autowired
	private AbonnementRepository abonnementRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	

	@Override
	public Abonnement creer(Abonnement entity) throws InvalideTogetException {

		return abonnementRepository.save(entity);
	}

	@Override
	public Abonnement modifier(Abonnement entity) throws InvalideTogetException {
		//Abonnement db = abonnementRepository.findPersonneParId(entity.getId());
	//	Abonnement db1 = abonnementRepository.findAbonneParId(entity.getId());
		//Membre p = db.getMembre();
		//Personne p1 = personnesRepository.getPersonneByid(p.getId());
		//personnesRepository.save(p1);
		return abonnementRepository.save(entity);
	}

	@Override
	public Abonnement modifierVue(Long idPersonne, Long idBlock) throws InvalideTogetException {

		Abonnement db = abonnementRepository.findAbonnementIdPerAndIdBlock(idPersonne, idBlock);
		Abonnement db1 = abonnementRepository.findAbonneParId(db.getId());
		int nombreVue = db1.getNombreVue();
		nombreVue++;
		db1.setNombreVue(nombreVue);

		return abonnementRepository.save(db1);
	}

	@Override
	public List<Abonnement> findAll() {

		return abonnementRepository.findAll();

	}

	@Override
	public Abonnement findById(Long id) {

		return abonnementRepository.findAbonneParId(id);
	}

	// la liste des personne par block
	@Override
	public List<Abonnement> personneALLBlock(long id) {
		return abonnementRepository.personneALLBlock(id);

	}

	@Override
	public List<Abonnement> detailBlocksPersonneParId(Long id) {

		return abonnementRepository.findAbonneParIdPersonne(id);
	}

	@Override
	public List<Abonnement> detailBlocksPersonneParLogin(String login) {

		return abonnementRepository.findAbonnementParPersonneLogin(login);
	}

	@Override
	public void creerUnAbonnement(Long id) throws InvalideTogetException {

	}

	@Override
	public List<Abonnement> tousLesDetailBlock() {

		return abonnementRepository.findAll();
	}

	// abonne est creer lorque la personne a paye le montant desirer
	// alors son statut est mis a Abonne qui est une enumeration

	
	@Override
	public boolean supprimer(Long id) {

		abonnementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Abonnement> entites) {
		abonnementRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {

		return abonnementRepository.existsById(id);
	}

	/*@Override
	public List<Abonnement> chercherPersonneParSpecialite(String specialite) {

		return abonnementRepository.chercherPersonneParSpecialite(specialite);
	}*/

	@Override
	public List<Abonnement> findDtailBlocksParIdBlock(Long id) {

		return abonnementRepository.findAbonnementParIdBlock(id);
	}

	

	@Override
	public List<Abonnement> chercherPersonneParVille(String ville) {

		return abonnementRepository.chercherPersonneParVille(ville);
	}
	@Override
	public List<Abonnement> abonnesSpecial() {
		List<Abonnement> abonnes = abonnementRepository.findAll();

		List<Abonnement> abonneSpecial = abonnes.stream().filter(p -> p.isAbonneSpecial()==true).limit(50).collect(Collectors.toList());

		return abonneSpecial;

	}

	@Override
	public List<Abonnement> chercherPersonneParSpecialite(String specialite) {
		// TODO Auto-generated method stub
		return null;
	}

}
