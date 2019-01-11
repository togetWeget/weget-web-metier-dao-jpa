package ci.weget.web.entites;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_HistoriquePanier")
public class HistoriquePanier  extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	private LocalDateTime date;
	private double quantite;
	private double montant;
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Block",unique=true)
	private Block block;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;

	public HistoriquePanier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HistoriquePanier(LocalDateTime date, double quantite, double montant, Block block, Personne personne) {
		super();
		this.date = date;
		this.quantite = quantite;
		this.montant = montant;
		this.block = block;
		this.personne = personne;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	
}
