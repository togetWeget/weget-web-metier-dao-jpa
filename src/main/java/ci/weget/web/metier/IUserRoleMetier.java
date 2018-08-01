package ci.weget.web.metier;



import ci.weget.web.entites.Personne;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

public interface IUserRoleMetier extends Imetier<UserRoles, Long> {
	UserRoles ajoutUserRole(Personne personne, AppRoles role);
}
