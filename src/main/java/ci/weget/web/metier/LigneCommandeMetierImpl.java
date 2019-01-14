package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.LigneCommandeRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.LigneCommande;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class LigneCommandeMetierImpl implements ILigneCommandeMetier {
@Autowired
LigneCommandeRepository ligneCommandeRepository;
	@Override
	public LigneCommande creer(LigneCommande entity) throws InvalideTogetException {
		
		return ligneCommandeRepository.save(entity);
	}

	@Override
	public LigneCommande modifier(LigneCommande entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.save(entity);
	}

	@Override
	public List<LigneCommande> findAll() {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findAll();
	}

	@Override
	public LigneCommande findById(Long id) {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		
		 ligneCommandeRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<LigneCommande> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ajoutLigneCommande(Block block, Personne personne, double quantite, double montant) {
		
		LigneCommande p = new LigneCommande();
		 List<LigneCommande> ligneCommandes = ligneCommandeRepository.findAllLigneCommandeParPersonneId(personne.getId()) ; 
         for (LigneCommande pa : ligneCommandes) {
 			// on a le block du panier de la personne
 			if (pa.getBlock().getId() == block.getId()) {
 			throw new RuntimeException("ce block existe deja dans votre panier");
 			}
 		}
     
		p.setBlock(block);
		p.setPersonne(personne);
		p.setQuantite(quantite);
		p.setMontant(montant);

		System.out.println("le panier" + p);
		ligneCommandeRepository.save(p);
		return true;
	}

	@Override
	public List<LigneCommande> findLigneCommandeParPersonneId(Long id) {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findAllLigneCommandeParPersonneId(id);
	}

	
}
