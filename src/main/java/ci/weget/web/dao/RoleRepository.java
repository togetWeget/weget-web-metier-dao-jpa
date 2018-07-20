package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.security.AppRoles;



public interface RoleRepository extends JpaRepository<AppRoles, Long>{
	// recherche d'un rôle via son nom
		AppRoles findByNom(String roleName);

		// recuperer un role a partir de son identifiant
		@Query("select r from AppRoles r where r.id=?1")
		AppRoles getRoleByid(Long id);
}
