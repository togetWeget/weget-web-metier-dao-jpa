package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Type_Statut")
public class TypeStatut extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	
	
	public TypeStatut() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TypeStatut(String libelle) {
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
