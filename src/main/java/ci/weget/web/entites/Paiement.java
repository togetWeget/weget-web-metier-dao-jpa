package ci.weget.web.entites;

import java.time.LocalDate;

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

	
	public boolean paye;
	private LocalDate date;

	private String motif;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personnes personne;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersonne;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;
	@Column(name = "id_Tarif", insertable = false, updatable = false)
	private long idTarif;

	public Paiement() {
		super();
	}

	

	public Paiement(Personnes personne) {
		super();
		this.personne = personne;
	}

	

	public boolean isPaye() {
		return paye;
	}

	public void setPaye(boolean paye) {
		this.paye = paye;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Personnes getPersonne() {
		return personne;
	}

	public void setPersonne(Personnes personne) {
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

}
