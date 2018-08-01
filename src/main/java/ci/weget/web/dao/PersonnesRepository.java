package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Personne;
import ci.weget.web.security.AppRoles;

public interface PersonnesRepository extends JpaRepository<Personne, Long> {

	// liste des roles d' une personne identoifie par son identifiant
	@Query("select ur.roles from UserRoles ur where ur.personne.id=?1")
	List<AppRoles> getRoles(long id);

	// liste des roles d'un utilisateur identifie par son login et son mot de passe
	@Query("select ur.roles from UserRoles ur where ur.personne.login=?1 and ur.personne.password=?2")
	List<AppRoles> getRoles(String login, String password);

	// recherche d'un utilisateur via son login
	Personne findByLogin(String login);

	// recuperer une personne par son type
	List<Personne> findByType(String type);

	// recupere une personne par son nom
	Personne findByNom(String nom);

	// rechercher une personne par son type et son nomComplet
	@Query("select p from Personne p where p.type = ?1 AND p.nomComplet like %?2%")
	List<Personne> findAllPersonnesParMc(String type, String mc);

	// ramener la liste des administrateurs
	@Query("select ad from Administrateur ad ")
	List<Personne> findAllAdministrateurs();

	// liste des personne par leur nom complet
	List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet);

	// liste des personne d'un block identifie par son libelle
	@Query("select db.personne from DetailBlock db where db.block.libelle=?1")
	List<Personne> getPersonneParBlockLibelle(String libelle);

	// liste des personnes d'un block identifie par son id
	@Query("select db.personne from DetailBlock db where db.block.id=?1")
	List<Personne> getPersonnesParBlock(long id);

	// liste des blocks d'une personne identifie par son id
	@Query("select db.block from DetailBlock db where db.personne.id=?1")
	List<Block> getBlockParPersonneId(long id);

	// recuperer une personne a partir de son identifiant
	@Query("select p from Personne p where p.id=?1")
	Personne getPersonneByid(Long id);

	///////////////// liste des abonnees dans la base//////////////
	@Query("select p from Personne p where p.typeStatut.libelle='Abonne'")
	List<Personne> getAllAbonnes();

	// rechercher un abonne par competence
	@Query("select p from Personne p where p.cvPersonne.specialite=?1 AND p.typeStatut.libelle='Abonne' ")
	List<Personne> chercherPersonneParSpecialite(String specialite);

	// rechercher une abonne par id
	@Query("select p from Personne p where p.id=?1 AND p.typeStatut.libelle='Abonne' ")
	public List<Personne> chercherAbonneParId(Long id);

}
