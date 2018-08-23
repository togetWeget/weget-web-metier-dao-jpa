package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Panier")
public class Panier extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Block")
	private Block block;
	private String date;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	private Double quantite;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;

	private Double total;

	public Panier() {
		super();

	}

	public Panier(Block block, Tarif tarif, Personne personne) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		this.block = block;
	}

	public Panier(Block block, Tarif tarif, Personne personne, String date, Double quantite, Double total) {
		super();
		this.tarif = tarif;
		this.personne = personne;
		this.block = block;
		this.quantite = quantite;
		this.total = total;
		this.date = date;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Panier [block=" + block + ", tarif=" + tarif + ", personne=" + personne + ", quantite=" + quantite
				+ ", total=" + total + ", date=" + date + "]";
	}

}
