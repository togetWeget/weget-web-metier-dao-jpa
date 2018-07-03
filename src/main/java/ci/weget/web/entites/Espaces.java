package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Espaces")
public class Espaces extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private double prix;
	private String description;
	public boolean paye;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_personnes")
	private Personnes personnes;
	@Column(name = "id_personnes",insertable = false, updatable = false)
	private long idPersonnes;

	public Espaces() {
		super();
	}

	public Espaces(double prix) {
		super();
		this.prix = prix;
	}

	

	public Espaces(String description) {
		super();
		this.description = description;
	}

	public Espaces(double prix, String description) {
		super();
		this.prix = prix;
		this.description = description;
	}

	
	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public boolean isPaye() {
		return paye;
	}

	public void setPaye(boolean paye) {
		this.paye = paye;
	}

	public Personnes getPersonnes() {
		return personnes;
	}

	public void setPersonnes(Personnes personnes) {
		this.personnes = personnes;
	}


}
