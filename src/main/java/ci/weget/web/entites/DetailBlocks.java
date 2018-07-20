package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="T_Detail_Blocks")
public class DetailBlocks extends AbstractEntity {
	
	private String description;
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Blocks")
	private Blocks blocks;
	@Column(name = "id_Blocks", insertable = false, updatable = false)
	private long idBlocks;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personnes personne;
	@Column(name = "id_Personne", insertable = false, updatable = false)
	private long idPersone;
	
	
	public DetailBlocks() {
		super();
		
	}
	
	
	public DetailBlocks(Blocks blocks, Personnes personne) {
		super();
		this.blocks = blocks;
		this.personne = personne;
	}


	public Blocks getBlocks() {
		return blocks;
	}
	public void setBlocks(Blocks blocks) {
		this.blocks = blocks;
	}
	
	
	public Personnes getPersonne() {
		return personne;
	}


	public void setPersonne(Personnes personne) {
		this.personne = personne;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	}
