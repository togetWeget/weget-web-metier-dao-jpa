package ci.weget.web.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Role;
import ci.weget.web.entites.RoleName;
import ci.weget.web.security.AppRoles;



public interface RoleRepository extends JpaRepository<Role, Long>{
	@Query("select r from Role r where r.name=?1")
	Role getUserRoleParName(RoleName roleName);

		AppRoles getRoleByid(Long id);
		Optional<Role> findByName(RoleName roleName);
}
