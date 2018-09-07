package ci.weget.web.metier;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Paiement;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.TypeStatut;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
public class DetailBlocksMetierImpl implements IDetailBlocksMetier {

	@Autowired
	private DetailBlocksRepository detailBlocksRepository;
	@Autowired
	private BlocksRepository blocksRepository;
	@Autowired
	private PersonnesRepository personnesRepository;

	
	

	@Override
	public DetailBlock creer(DetailBlock entity) throws InvalideTogetException {

		return null;
	}

	@Override
	public DetailBlock modifier(DetailBlock entity) throws InvalideTogetException {
       DetailBlock db = detailBlocksRepository.findDtailBlocksParId(entity.getId());
		Personne p= db.getPersonne();
		Personne p1= personnesRepository.getPersonneByid(p.getId());
		personnesRepository.save(p1);
       return db;
	}

	
	@Override
	public List<DetailBlock> findAll() {

		return null;
	}

	@Override
	public DetailBlock findById(Long id) {

		return detailBlocksRepository.findDtailBlocksParId(id);
	}

	// la liste des personne par block
	@Override
	public List<DetailBlock> personneALLBlock(long id) {
		return detailBlocksRepository.personneALLBlock(id);

	}

	@Override
	public List<DetailBlock> detailBlocksPersonneParId(Long id) {

		return detailBlocksRepository.findDtailBlocksParPersonneId(id);
	}

	@Override
	public List<DetailBlock> detailBlocksPersonneParLogin(String login) {

		return detailBlocksRepository.findDtailBlocksParPersonneLogin(login);
	}
	@Override
	public void addPersonneToBlocks(String login, String libelle) throws InvalideTogetException {

		Personne personne = personnesRepository.findByLogin(login);
		Block block = blocksRepository.findByLibelle(libelle);
		DetailBlock db = new DetailBlock(personne, block);
		detailBlocksRepository.save(db);

	}

	@Override
	public List<DetailBlock> tousLesDetailBlock() {

		return detailBlocksRepository.findAlldetailBlock();
	}

	// abonne est creer lorque la personne a paye le montant desirer
	// alors son statut est mis a Abonne qui est une enumeration
	@Override
	public boolean creerAbonne(String login, String libelle)  {
		try {
			addPersonneToBlocks(login, libelle);
		} catch (InvalideTogetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
		}

	// abonne est creer lorque la personne a paye le montant desirer
	// alors son statut est mis a Abonne qui est une enumeration
	@Override
	public boolean creerAbonneSpecial(String login, String libelle)  {
		try {
			addPersonneToBlocks(login, libelle);
		} catch (InvalideTogetException e) {
		
			e.printStackTrace();
		}
		return true;
	}
	// ramener les abonne a afficher sur la page d'accueil
			@Override
			public List<DetailBlock> abonneSpecial(DetailBlock d) {
				List<DetailBlock> pers = detailBlocksRepository.findAll();

				List<DetailBlock> db = pers.stream().filter(x -> d.getPersonne().isActived()).collect(Collectors.toList());

				return db;

			}


	@Override
	public boolean supprimer(Long id) {

		return false;
	}

	@Override
	public boolean supprimer(List<DetailBlock> entites) {

		return false;
	}

	@Override
	public boolean existe(Long id) {

		return false;
	}

}
