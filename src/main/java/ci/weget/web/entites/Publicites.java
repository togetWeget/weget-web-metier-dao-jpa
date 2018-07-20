package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_Publicite")
public class Publicites extends AbstractEntity {


	private static final long serialVersionUID = 1L;
	private String libelle;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tarif")
	private List<Tarif> tarifs;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;
	@Column(name = "id_Entreprise",insertable = false, updatable = false)
	private long idEntreprise;
	
	public Publicites(String libelle, Entreprise entreprise) {
		super();
		this.libelle = libelle;
		this.entreprise = entreprise;
	}
	public Publicites() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public List<Tarif> getTarifs() {
		return tarifs;
	}
	public void setTarifs(List<Tarif> tarifs) {
		this.tarifs = tarifs;
	}
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
	
}
