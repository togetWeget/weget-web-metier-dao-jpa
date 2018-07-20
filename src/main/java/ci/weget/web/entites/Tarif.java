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

	private Float montant;
	private String description;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_block")
	private Blocks block;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_publicite")
	private Publicites publicite;
    public Tarif() {
		super();
		
	}
	

	public Tarif(Float montant, String description) {
		super();
		this.montant = montant;
		this.description = description;
		
	}

	public Float getMontant() {
		return montant;
	}

	public void setMontant(Float montant) {
		this.montant = montant;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "Tarif [montant=" + montant + ", description=" + description + "]";
	}


	public Blocks getBlock() {
		return block;
	}


	public void setBlock(Blocks block) {
		this.block = block;
	}


	public Publicites getPublicite() {
		return publicite;
	}


	public void setPublicite(Publicites publicite) {
		this.publicite = publicite;
	}

	

	
}
