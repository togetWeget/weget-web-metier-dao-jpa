package ci.weget.web.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.DetailAbonnementRepository;
import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.Reponse;

@Service
public class PanierMetierImpl implements IPanierMetier {

	private Map<Long, Panier> items = new HashMap<>();
	@Autowired
	private TarifRepository tarifRepository;
	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private AbonnementRepository detailBlocksRepository;
	@Autowired
	private BlocksRepository blocksRepository;
	@Autowired
	private DetailAbonnementRepository sousBlocksRepository;

	@Override
	public boolean ajoutLignePanier(Tarif tarif, Block block, Personne personne, double quantite, double montant) {
		
		Panier p = new Panier();
		 List<Panier> paniers = panierRepository.findAllPanierParPersonneId(personne.getId()) ; 
         for (Panier pa : paniers) {
 			// on a le block du panier de la personne
 			if (pa.getBlock().getId() == block.getId()) {
 			throw new RuntimeException("ce block existe deja dans votre panier");
 			}
 		}
       /*  // creation d'un abonne apres paiement
        * definir le role de abonne a abonne
        Membre membre = personnesRepository.findByLogin(personne.getLogin());
 		block = blocksRepository.findByLibelle(block.getLibelle());
 		DetailBlock db = new DetailBlock();
 		db.setBlock(block);
 		db.setMembre(membre);
 		db.setNombreVue(0);
 		db.setPathPhoto(null);
 		detailBlocksRepository.save(db);
         
     if (block.getTypeBlock()=="ecole") {
		SousBlock sb= new SousBlock();
		sb.setIdBlock(block.getId());
		sb.setDetailBlock(db);
		sousBlocksRepository.save(sb);
			}*/
		p.setBlock(block);
		p.setTarif(tarif);
		p.setPersonne(personne);
		p.setQuantite(quantite);
		p.setMontant(montant);

		System.out.println("le panier" + p);
		panierRepository.save(p);
		return true;
	}

	@Override
	public Panier creer(Panier panier) throws InvalideTogetException {
		return panierRepository.save(panier);

	}

	@Override
	public boolean modifLignePanier(Long id,Tarif tarif, Block block, Personne personne, double quantite, double montant) {
		
		 Panier p = panierRepository.findPanierById(id);
        
         
         p.setBlock(block);
		p.setTarif(tarif);
		p.setPersonne(personne);
		p.setQuantite(quantite);
		p.setMontant(montant);

		System.out.println("le panier" + p);
		panierRepository.save(p);
		return true;
	}

	@Override
	public List<Panier> findAll() {

		return panierRepository.findAllPanier();
	}

	@Override
	public Panier findById(Long id) {

		return panierRepository.findPanierById(id);
	}

	@Override
	public boolean supprimer(Long id) {

		panierRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Panier> entites) {

		panierRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Collection<Panier> getItems() {
		return items.values();

	}

	public double getTotal() {
		double total = 0d;
		for (Panier lc : items.values()) {
			total += lc.getTarif().getPrix() * lc.getQuantite();

		}
		return total;

	}

	public int getSize() {
		return items.size();
	}

	@Override
	public List<Panier> LesPanierDeLaPersonne(long idPersonne) {

		return panierRepository.findAllPanierParPersonneId(idPersonne);
	}

	@Override
	public Panier modifier(Panier entity) throws InvalideTogetException {
		
		return null;
	}

}
