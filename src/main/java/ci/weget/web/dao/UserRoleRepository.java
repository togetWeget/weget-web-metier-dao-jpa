package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.security.UserRoles;



public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

}
