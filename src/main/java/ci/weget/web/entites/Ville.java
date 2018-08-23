package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_VILLE")
public class Ville extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	private String description;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Pays")
	private Pays pays;
	public Ville() {
		super();

	}

	public Ville(String libelle, String description) {
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
