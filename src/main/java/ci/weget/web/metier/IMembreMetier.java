package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.Panier;

public interface IMembreMetier  {
public Personne personneParId(final Long id);
public Personne ajouterMembres(Personne p) throws InvalideTogetException;
public Personne metteAjourSonProil(Personne p) throws InvalideTogetException;
public Commande enregistrerCommande(Panier p, Personne pers);
public List<Personne> chercherPersonneParCompetence(String competence);
public List<Personne>chercherAbonneParId(Long id);
}
