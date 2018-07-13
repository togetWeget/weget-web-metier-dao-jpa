package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Type_Statut")
public class Type_Statut extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	
	
	public Type_Statut() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Type_Statut(String libelle) {
		super();
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	

}
