package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.security.AppRoles;



public interface RoleRepository extends JpaRepository<AppRoles, Long>{
	// recherche d'un rôle via son nom
		AppRoles findByNom(String roleName);
}
