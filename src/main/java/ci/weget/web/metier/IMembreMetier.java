package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;


public interface IMembreMetier extends Imetier<Personne, Long> {
	public Commande enregistrerCommande(Panier p, Personne pers);

	public List<Personne> chercherPersonneParCompetence(String competence);

	public List<Personne> chercherAbonneParId(Long id);

	public Personne findByLogin(String login);

	public void addPersonneToBlocks(String login, String libelle) throws InvalideTogetException;

	public Personne creerAbonne(String login, String libelle) throws InvalideTogetException;
	public Personne creerAbonneSpecial(String login, String libelle) throws InvalideTogetException;

	public void addRoleToUser(String userName, String RoleName);

	Personne findAbonneByLogin(String login) throws InvalideTogetException;

	// la liste des abonnes
	public List<Personne> getAllAbonnes();

	List<DetailBlock> lesAbonneParBlock(Long id);

	List<Personne> personneALL(String type);
	List<DetailBlock> membreAbonne(DetailBlock p);

}
