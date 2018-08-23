package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Tarif")
public class Tarif extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private Double prix;
	private String dureeTarif;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Block", insertable = false, updatable = false)
	private Block block;

	@Column(name = "id_Block")
	private long idBlock;

	public Tarif() {
		super();

	}

	public Tarif(Block block, String description, String dureeTarif,Double prix,String titre ) {
		super();
		this.titre = titre;
		this.prix = prix;
		this.dureeTarif = dureeTarif;
		this.description = description;
		this.block = block;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public String getDureeTarif() {
		return dureeTarif;
	}

	public void setDureeTarif(String dureeTarif) {
		this.dureeTarif = dureeTarif;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public long getIdBlock() {
		return idBlock;
	}

}
