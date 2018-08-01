package ci.weget.web.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;
import ci.weget.web.entites.Administrateur;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;



@Entity
@Table(name="T_UserRoles")
public class UserRoles extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	@Column(name = "id_personne", insertable = false, updatable = false)
	private long idPersonne;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "id_roles")
	private AppRoles roles;
	@Column(name = "id_Roles", insertable = false, updatable = false)
	private long idRoles;

	public UserRoles() {
		super();
		
	}

	
	public UserRoles(Personne personne, AppRoles roles) {
		super();
		this.personne = personne;
		this.roles = roles;
	}


	public Personne getPersonne() {
		return personne;
	}


	public void setPersonnes(Personne personnes) {
		this.personne = personnes;
	}


	public AppRoles getRoles() {
		return roles;
	}

	public void setRoles(AppRoles roles) {
		this.roles = roles;
	}

	
}
