package ci.weget.web.metier;

import ci.weget.web.entites.Role;
import ci.weget.web.entites.RoleName;

public interface IRoleMetier extends Imetier<Role, Long> {
	public Role getUserRoleByNom(RoleName name);
}
