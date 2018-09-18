package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Detail_Block")
public class DetailBlock extends AbstractEntity {

	private String description;
	private String pathPhoto;
	private String pathPhotoCouveture;

	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Personne")
	private Membre membre;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Block")
	private Block block;
	
	private int nombreVue;

	/*
	 * @Column(name = "id_Block", insertable = false, updatable = false) private
	 * long idBlock;
	 * 
	 * @Column(name = "id_Personne", insertable = false, updatable = false) private
	 * long idPersone;
	 */

	public DetailBlock() {
		super();

	}

	public DetailBlock(Membre membre, Block block) {
		super();
		this.membre = membre;
		this.block = block;
	}

	public DetailBlock(String description, String pathPhoto, String pathPhotoCouveture, Membre membre, Block block) {
		super();
		this.description = description;
		this.pathPhoto = pathPhoto;
		this.pathPhotoCouveture = pathPhotoCouveture;
		this.membre = membre;
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlocks(Block block) {
		this.block = block;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * public long getIdBlock() { return idBlock; }
	 * 
	 * public long getIdPersone() { return idPersone; }
	 */

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getPathPhotoCouveture() {
		return pathPhotoCouveture;
	}

	public void setPathPhotoCouveture(String pathPhotoCouveture) {
		this.pathPhotoCouveture = pathPhotoCouveture;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public int getNombreVue() {
		return nombreVue;
	}

	public void setNombreVue(int nombreVue) {
		this.nombreVue = nombreVue;
	}

	
}
