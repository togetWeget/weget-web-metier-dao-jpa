package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.security.UserRoles;



public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
	// liste des userRoles d'une personne
		@Query("select ur from UserRoles ur left join fetch ur.roles left join fetch ur.personne p where p.id=?1")
		public List<UserRoles> roleParPersonneId(Long id);
}
