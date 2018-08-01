package ci.weget.web.metier;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CommandeRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.LigneCommandeRepo;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.LigneCommande;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.Panier;

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
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Personne personneParId(final Long id) {
		
		return personnesRepository.getPersonneByid(id);
	}

	
	@Override
	public Personne ajouterMembres(Personne p) throws InvalideTogetException{
		if(!p.getPassword().equals(p.getRepassword())) {
			throw new InvalideTogetException("Vous devez remplir des mots de passe identique");
		} 
		Personne pers = null;
		
		try {
			pers = personnesRepository.getPersonneByid(p.getId());
			
		} catch (Exception e) {
			throw new InvalideTogetException("probleme de connexion");
		}
		if (pers!=null) {
			throw new InvalideTogetException("ce login est deja utilise");
		}
		
        String hshPW = bCryptPasswordEncoder.encode(p.getPassword());
        String hshRPW = bCryptPasswordEncoder.encode(p.getRepassword());
		p.setPassword(hshPW);
		p.setRepassword(hshRPW);
		return personnesRepository.save(p);
	}

	@Override
	public Personne metteAjourSonProil(Personne modif) throws InvalideTogetException {
		Personne p = personnesRepository.getPersonneByid(modif.getId());

		if (p != null && p.getId() != modif.getId()) {

			throw new InvalideTogetException("cette personne est deja une autre personne");

		}

		return personnesRepository.save(modif);
	}

	@Override
	public List<Personne> chercherPersonneParCompetence(String specialite) {
		
		return personnesRepository.chercherPersonneParSpecialite(specialite);
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

	
}
