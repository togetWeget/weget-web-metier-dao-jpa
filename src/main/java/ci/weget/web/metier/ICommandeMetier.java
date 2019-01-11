package ci.weget.web.metier;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICommandeMetier extends Imetier<Commande, Long> {
public Commande	creerCommande(Personne p,double total) throws InvalideTogetException;
Commande commandeParPersonne(Long id);

}
