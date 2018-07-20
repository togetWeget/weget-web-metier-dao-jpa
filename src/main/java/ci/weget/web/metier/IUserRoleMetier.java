package ci.weget.web.metier;



import ci.weget.web.entites.Personnes;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

public interface IUserRoleMetier extends Imetier<UserRoles, Long> {
	UserRoles ajoutUserRole(Personnes personne, AppRoles role);
}
