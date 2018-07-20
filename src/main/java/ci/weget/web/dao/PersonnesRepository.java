package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Personnes;
import ci.weget.web.security.AppRoles;

public interface PersonnesRepository extends JpaRepository<Personnes, Long> {

	// liste des roles d' une personne identoifie par son identifiant
	@Query("select ur.roles from UserRoles ur where ur.personnes.id=?1")
	List<AppRoles> getRoles(long id);

	// liste des roles d'un utilisateur identifie par son login et son mot de passe
	@Query("select ur.roles from UserRoles ur where ur.personnes.login=?1 and ur.personnes.password=?2")
	List<AppRoles> getRoles(String login, String password);

	// recherche d'un utilisateur via son login
	Personnes findByLogin(String login);

	// recuperer une personne par son type
	List<Personnes> findByType(String type);

	// recupere une personne par son nom
	Personnes findByNom(String nom);

	// rechercher une personne par son type et son nomComplet
	@Query("select p from Personnes p where p.type = ?1 AND p.nomComplet like %?2%")
	List<Personnes> findAllPersonnesParMc(String type, String mc);

	// ramener la liste des administrateurs
	@Query("select ad from Administrateurs ad ")
	List<Personnes> findAllAdministrateurs();

	// liste des personne par leur nom complet
	List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet);

	// liste des personne d'un block identifie par son libelle
	@Query("select db.personne from DetailBlocks db where db.blocks.libelle=?1")
	List<Personnes> getPersonneParBlockLibelle(String libelle);

	// liste des personnes d'un block identifie par son id
	@Query("select db.personne from DetailBlocks db where db.blocks.id=?1")
	List<Personnes> getPersonnesParBlock(long id);

	// liste des blocks d'une personne identifie par son id
	@Query("select db.blocks from DetailBlocks db where db.personne.id=?1")
	List<Blocks> getBlockParPersonneId(long id);

	// recuperer une personne a partir de son identifiant
	@Query("select p from Personnes p where p.id=?1")
	Personnes getPersonneByid(Long id);
	
	///////////////// liste des abonnees dans la base//////////////
	@Query("select p from Personnes p where p.typeStratut.libelle=Abonne")
	List<Personnes> getAllAbonnes();

}
