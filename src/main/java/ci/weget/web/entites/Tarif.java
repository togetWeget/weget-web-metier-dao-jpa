package ci.weget.web.entites;

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

	private Float montant;
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Block")
	private Blocks block;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Publicite")
	private Publicites publicite;
	@Column(name = "id_Publicite", insertable = false, updatable = false)
	private long idPublicite;

	public Tarif() {
		super();
		
	}
	

	public Tarif(Float montant, String description, Blocks block, Publicites publicite) {
		super();
		this.montant = montant;
		this.description = description;
		this.block = block;
		this.publicite = publicite;
	}

	public Float getMontant() {
		return montant;
	}

	public void setMontant(Float montant) {
		this.montant = montant;
	}

	public Publicites getPublicite() {
		return publicite;
	}

	public void setPublicite(Publicites publicite) {
		this.publicite = publicite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blocks getBlock() {
		return block;
	}

	public void setBlock(Blocks block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "Tarif [montant=" + montant + ", description=" + description + ", block=" + block + "]";
	}

}
