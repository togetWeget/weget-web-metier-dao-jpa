package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Paiement")
public class Paiement extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String paye;
	private String date;

	private String motif;
	private Double montant;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersonne;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;
	@Column(name = "id_Tarif", insertable = false, updatable = false)
	private long idTarif;

	public Paiement() {
		super();
	}

	public Paiement(Personne personne) {
		super();
		this.personne = personne;
	}

	public Paiement(String paye, String date, String motif, Personne personne, Tarif tarif) {
		super();
		this.paye = paye;
		this.date = date;
		this.motif = motif;
		this.personne = personne;
		this.tarif = tarif;
	}
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	public long getIdPersonne() {
		return idPersonne;
	}

	public long getIdTarif() {
		return idTarif;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getPaye() {
		return paye;
	}

	public void setPaye(String paye) {
		this.paye = paye;
	}

}
