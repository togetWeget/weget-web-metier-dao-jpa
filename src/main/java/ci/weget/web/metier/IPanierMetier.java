package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Tarif;

public interface IPanierMetier extends Imetier<Panier, Long> {
  public boolean ajoutLignePanier(Tarif tarif, Block block, Personne personne,Double quantite,Double total);
  public boolean modifLignePanier(Long id,Long version,Tarif tarif, Block block, Personne personne,Double quantite,Double total);
  public List<Panier> LesPanierDeLaPersonne(long idPersonne);
}
