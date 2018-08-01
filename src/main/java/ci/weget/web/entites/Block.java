package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_Block")
public class Block extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String libelle;
	private String pathPhoto;
	private String description;
	@OneToMany()
	@JoinColumn(name = "id_Block")
	private  List<SousBlock> sousBlocks;
	public Block() {
		super();

	}

	public Block(String libelle, String pathPhoto) {
		super();
		this.libelle = libelle;
		this.pathPhoto = pathPhoto;
	}

	public Block(String libelle) {
		super();
		this.libelle = libelle;
	}
	

	public Block(String libelle, String pathPhoto, String description) {
		super();
		this.libelle = libelle;
		this.pathPhoto = pathPhoto;
		this.description = description;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	@Override
	public String toString() {
		return "Blocks [libelle=" + libelle + ", pathPhoto=" + pathPhoto + ", description=" + description + "]";
	}

}
