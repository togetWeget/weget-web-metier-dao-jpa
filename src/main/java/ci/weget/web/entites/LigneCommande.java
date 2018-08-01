package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ValidationException;

   @Entity
    public class LigneCommande extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private Integer quantite;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Block")
	private Block blocks;
	private Double prix;

	public LigneCommande() {
		super();
		
	}

	
	public LigneCommande(Integer quantite, Block blocks, Double prix) {
		super();
		this.quantite = quantite;
		this.blocks = blocks;
		this.prix = prix;
	}


	@PrePersist
	@PreUpdate
	private void validateData() {
		if (quantite == null || quantite < 0)
			throw new ValidationException("Invalid quantity");
	}


	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	
	
	public Block getBlocks() {
		return blocks;
	}


	public void setBlocks(Block blocks) {
		this.blocks = blocks;
	}


	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}
	
}
