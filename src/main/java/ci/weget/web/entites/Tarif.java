package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Tarif")
public class Tarif extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private Double prix;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_block")
	private Block block;
	

	public Tarif() {
		super();

	}

	

	public Tarif(Double prix, String description, Block block) {
		super();
		this.prix = prix;
		this.description = description;
		this.block = block;
	
	}



	public Double getPrix() {
		return prix;
	}



	public void setPrix(Double prix) {
		this.prix = prix;
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

	

}
