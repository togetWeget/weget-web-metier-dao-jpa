package ci.weget.web.security;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;



@Entity
@Table(name="T_Roles")
public class AppRoles extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private String nom;
	private String description;
	public AppRoles() {
		super();
	
	}
	
	public AppRoles(String nom) {
		super();
		this.nom = nom;
	}

	public AppRoles(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "AppRoles [nom=" + nom + ", description=" + description + "]";
	}
	
}
