package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_TypeStatut")
public class TypeStatut extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String libelle;
	@Column(columnDefinition = "TEXT")
	private String description;

	public TypeStatut() {
		super();

	}

	public TypeStatut(String libelle, String description) {
		super();
		this.libelle = libelle;
		this.description = description;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
