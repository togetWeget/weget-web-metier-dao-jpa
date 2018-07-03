package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	List<Personnes> findByType(String type);

	Personnes findByNom(String nom);

	@Query("select p from Personnes p where p.type = ?1 AND p.nomComplet like %?2%")
	List<Personnes> findAllPersonnesParMc(String type, String mc);

	@Query("select ad from Administrateurs ad ")
	List<Personnes> findAllAdministrateurs();
	
	@Query("select me from Membres me ")
	List<Personnes> findAllMembres();

	List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet);
	
	

}
