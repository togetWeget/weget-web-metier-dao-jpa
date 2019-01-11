package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface IAbonnementMetier extends Imetier<Abonnement, Long> {

	public List<Abonnement> personneALLBlock(long id);

	public List<Abonnement> detailBlocksPersonneParId(Long id);

	public List<Abonnement> detailBlocksPersonneParLogin(String login);

	public List<Abonnement> tousLesDetailBlock();

public void creerUnAbonnement(Long id) throws InvalideTogetException;

	public Abonnement modifierVue(Long idPersonne, Long idBlock) throws InvalideTogetException;

	List<Abonnement> chercherPersonneParSpecialite(String specialite);

	List<Abonnement> chercherPersonneParVille(String ville);

	List<Abonnement> findDtailBlocksParIdBlock(Long id);

//	public List<Abonnement> rechercherParCompetenceOuVille(String specialite, String ville);

	public List<Abonnement> abonnesSpecial();

}
