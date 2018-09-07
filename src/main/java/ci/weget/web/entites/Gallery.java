package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Gallery")
public class Gallery extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	private String libelle;
	@ElementCollection
	private List<String> pathPhotoGallery;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Membre")
	private Membre membre;

	public Gallery() {
		super();

	}

	public Gallery(String libelle, List<String> pathPhotoGallery, Membre membre) {
		super();
		this.libelle = libelle;
		this.pathPhotoGallery = pathPhotoGallery;
		this.membre = membre;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<String> getPathPhotoGallery() {
		return pathPhotoGallery;
	}

	public void setPathPhotoGallery(List<String> pathPhotoGallery) {
		this.pathPhotoGallery = pathPhotoGallery;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	
}
