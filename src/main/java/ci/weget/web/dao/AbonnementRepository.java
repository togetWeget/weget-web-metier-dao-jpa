package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.Personne;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

	// ramener les abonnes d'un block par libelle
	@Query("select ab from Abonnement ab  where ab.block.libelle=?1")
	List<Abonnement> findAllPersonnesParBlock(String libelle);

	// ramener une liste de detail block quand on connait l'id du block
	@Query("select ab from Abonnement ab  where ab.block.id=?1")
	List<Abonnement> findAllBlocksParPersonne(Long id);

	// ramener le detail block d'un block a partir de son id
	@Query("select ab from Abonnement ab where ab.id=?1")
	Abonnement findAbonneParId(Long id);

	// ramener les details blocks d'une personne a partir de son identifianr
	@Query("select ab from Abonnement ab left join fetch ab.block b left join fetch ab.membre p where p.id=?1")
	List<Abonnement> findAbonneParIdPersonne(Long id);

	// ramener tous les blocks d'une personnes par login
	@Query("select ab from Abonnement ab left join fetch ab.block b left join fetch ab.membre p where p.login=?1")
	List<Abonnement> findAbonnementParPersonneLogin(String login);

	// ramener une personne a partir de detail block
	@Query("select ab from Abonnement ab left join fetch ab.block b left join fetch ab.membre p where p.id=?1")
	Abonnement findPersonneParId(Long id);

	// ramener le detail block a partir de block et de personne
	@Query("select ab from Abonnement ab left join fetch ab.block b left join fetch ab.membre p where p.id=?1 AND  b.id=?2")
	Abonnement findAbonnementIdPerAndIdBlock(Long idPersonne, Long idBlock);

	// ramener les abonnes d'un block par id
	@Query("select ab from Abonnement ab  where ab.block.id=?1")
	List<Abonnement> personneALLBlock(Long id);

	// rechercher un abonne par competence
	/*@Query("select ab from Abonnement ab  where ab.membre.cvPersonne.specialite=?1")
	List<Abonnement> chercherPersonneParSpecialite(String specialite);*/
	// rechercher un abonne par ville
		@Query("select ab from Abonnement ab  where ab.membre.adresse.ville=?1")
		List<Abonnement> chercherPersonneParVille(String ville);

	// ramener tous les detail blocks identifiant du block
	@Query("select ab from Abonnement ab left join fetch ab.block b left join fetch ab.membre p where ab.block.id=?1")
	List<Abonnement> findAbonnementParIdBlock(Long id);

	/*// rechercher une personne abonne par sa mot cle ou sa ville
	@Query("select ab from Abonnement ab  where ab.membre.cvPersonne.specialite=?1 AND ab.membre.adresse.ville=?2")
	List<Abonnement> rechercherParCompetenceOuVille(String specialite, String ville);*/
/*
	// rechercher une personne par sa competence ou sa ville
		@Query("select ab from Abonnement ab where ab.cvPersonne.specialite=?1")
		List<Abonnement> findParCompetence(String competence);*/
///////////////// liste des abonnees dans la base//////////////
@Query("select ab from Abonnement ab")
List<Abonnement> getAllAbonnes();
//rechercher une abonne par id
	@Query("select ab from Abonnement ab where ab.id=?1")
	public List<Abonnement> chercherAbonneParId(long id);

	// liste des personne d'un block identifie par son libelle
	@Query("select db.membre from Abonnement db where db.block.libelle=?1")
	List<Personne> getPersonneParBlockLibelle(String libelle);
	// liste des personnes d'un block identifie par son id
		@Query("select db.membre from Abonnement db where db.block.id=?1")
		List<Abonnement> getPersonnesParBlock(long id);

}
