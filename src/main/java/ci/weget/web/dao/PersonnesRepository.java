package ci.weget.web.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ci.weget.web.entites.Administrateur;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;

public interface PersonnesRepository extends JpaRepository<Personne, Long> {

	// recuperer une personne via son login
	@Query("select p from Personne p where p.login=?1")
	Membre findByMembreLogin(String login);

	// recuperer une personne par son type
	List<Personne> findByType(String type);

	// recuperer un admin via son login
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

	// recuperer un membre a partir de son identifiant
	@Query("select m from Membre m where m.id=?1")
	Membre getMembreByid(long id);

	// liste des personne de la base a partir de id 
	List<Personne> findByIdIn(List<Long> userIds);
	
	// liste des personne de la base a partir de login 
	Optional<Personne> findByLogin(String login);

	// verifier si une personne existe a partir de son login
	Boolean existsByLogin(String login);

	// recuper une personne via son login
	@Query("select p from Personne p where p.login=?1")
	Personne findPersonneByLogin(String login);

}
