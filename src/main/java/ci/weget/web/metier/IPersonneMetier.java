package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Personnes;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

public interface IPersonneMetier extends Imetier<Personnes, Long> {

	Personnes findById(final Long id);

	List<AppRoles> getRoles(long id);

	List<AppRoles> getRoles(String login, String password);

	Personnes findByNom(String nom);

	List<Personnes> findAllPersonnesParMc(String type, String mc);

	List<Personnes> findAllAdministrateurs();

	List<Personnes> findAllMembres();

	List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet);

	List<Personnes> personneALL(String type);

	public AppRoles saveRole(AppRoles role);

	public UserRoles saveUserRole(UserRoles ur);

	public void addRoleToUser(String userName, String RoleName);

	public Personnes findPersonnesByLogin(String login);

	public void addPersonneToBlocks(String login, String libelle);
	
	// creer un abonnee
	public Personnes creerAbonne(Personnes personne);
	// la liste des abonnes
	public List<Personnes> getAllAbonnes();
	
	}
