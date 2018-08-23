package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Personne;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

public interface IAdminMetier extends Imetier<Personne, Long> {

	Personne findById(final Long id);

	List<AppRoles> getRoles(long id);

	List<AppRoles> getRoles(String login, String password);

	Personne findByNom(String nom);

	List<UserRoles> roleParPersonneId(Long id);

	List<Personne> findAllPersonnesParMc(String type, String mc);

	List<Personne> findAllAdministrateurs();

	List<Personne> findByNomCompletContainingIgnoreCase(String nomcomplet);

	List<Personne> personneALL(String type);

	public AppRoles saveRole(AppRoles role);

	public UserRoles saveUserRole(UserRoles ur);

	public void addRoleToUser(String userName, String RoleName);

	public Personne findPersonnesByLogin(String login);

	



	public boolean supprimer(Long id);

}
