package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Administrateur;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.security.AppRoles;

public interface PersonnesRepository extends JpaRepository<Personne, Long> {
	// liste des roles d'une personne identifie par son identifiant
	@Query("select ur.roles from UserRoles ur where ur.personne.id=?1")
	List<AppRoles> getRoles(long id);

	// liste des roles d'un utilisateur identifie par son login et son mot de passe
	@Query("select ur.roles from UserRoles ur where ur.personne.login=?1 and ur.personne.password=?2")
	List<AppRoles> getRoles(String login, String password);

	// recherche d'un utilisateur via son login
	@Query("select p from Personne p where p.login=?1")
	Membre findByLogin(String login);

	// recuperer une personne par son type
	List<Personne> findByType(String type);

	// recherche d'un utilisateur via son login
	@Query("select p from Personne p where p.login=?1")
	Administrateur findAdminByLogin(String login);

	// recupere une personne par son nom
	Personne findByNom(String nom);

	// rechercher une personne par son type et son nomComplet
	@Query("select p from Personne p where p.type = ?1 AND p.nomComplet like %?2%")
	List<Personne> findAllPersonnesParMc(String type, String mc);

	// ramener la liste des administrateurs
	@Query("select ad from Administrateur ad ")
	List<Personne> findAllAdministrateurs();

	// ramener la liste des membres
	@Query("select me from Membre me ")
	List<Personne> findAllMembres();

	// liste des personne par leur nom complet
	List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet);

	// recuperer une personne a partir de son identifiant
	@Query("select p from Personne p where p.id=?1")
	Personne getPersonneByid(long id);

	///////////////// liste des abonnees dans la base//////////////
	@Query("select p from Personne p left join fetch p.typestatut t where t.libelle='abonne'")
	List<Personne> getAllAbonnes();

	// rechercher une abonne par id
	@Query("select p from Personne p where p.id=?1")
	public List<Personne> chercherAbonneParId(long id);

	// liste des personne d'un block identifie par son libelle
	@Query("select db.membre from DetailBlock db where db.block.libelle=?1")
	List<Personne> getPersonneParBlockLibelle(String libelle);

	// liste des personnes d'un block identifie par son id
	@Query("select db.membre from DetailBlock db where db.block.id=?1")
	List<Personne> getPersonnesParBlock(long id);

	}
