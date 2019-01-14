package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.LigneCommande;
import ci.weget.web.entites.Personne;

public interface ILigneCommandeMetier extends Imetier<LigneCommande, Long> {
	  public boolean ajoutLigneCommande(Block block, Personne personne,double quantite,double total);
	  public List<LigneCommande> findLigneCommandeParPersonneId(Long id);
}
