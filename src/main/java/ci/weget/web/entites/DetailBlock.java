package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Detail_Block")
public class DetailBlock extends AbstractEntity {

	private String description;

	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Personne")
	private Personne personne;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Block")
	private Block block;
	@Column(name = "id_Blocks", insertable = false, updatable = false)
	private long idBlocks;

	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersone;

	public DetailBlock() {
		super();

	}

	public DetailBlock(Personne personne, Block block) {
		super();
		this.personne = personne;
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlocks(Block block) {
		this.block = block;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}