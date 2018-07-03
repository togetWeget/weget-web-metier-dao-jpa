package ci.weget.web.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.Administrateurs;
import ci.weget.web.entites.Membres;
import ci.weget.web.entites.Personnes;



@Entity
@Table(name="T_UserRoles")
public class UserRoles extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_Personnes")
	private Personnes personnes;
	@Column(name = "id_personnes", insertable = false, updatable = false)
	private long idPersonnes;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "id_roles")
	private AppRoles roles;
	@Column(name = "id_Roles", insertable = false, updatable = false)
	private long idRoles;

	public UserRoles() {
		super();
		
	}

	
	public UserRoles(Personnes personnes, AppRoles roles) {
		super();
		this.personnes = personnes;
		this.roles = roles;
	}


	public Personnes getPersonnes() {
		return personnes;
	}


	public void setPersonnes(Personnes personnes) {
		this.personnes = personnes;
	}


	public AppRoles getRoles() {
		return roles;
	}

	public void setRoles(AppRoles roles) {
		this.roles = roles;
	}

	
}
