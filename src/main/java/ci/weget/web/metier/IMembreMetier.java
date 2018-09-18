package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface IMembreMetier extends Imetier<Personne, Long> {
	public Commande enregistrerCommande(Panier p, Personne pers);

	public Personne findByLogin(String login);

	public void addRoleToUser(String userName, String RoleName);

	List<Personne> personneALL(String type);

	

}
