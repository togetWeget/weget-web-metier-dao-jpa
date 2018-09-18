package ci.weget.web.entites;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_Gallery")
public class Gallery extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelle;
	private String description;
	private LocalDateTime date;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Gallery")
	private List<PhotoGallery> pathPhotoGallery;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_DetailBlock")
	private Membre membre;

	public Gallery() {
		super();

	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<PhotoGallery> getPathPhotoGallery() {
		return pathPhotoGallery;
	}

	public void setPathPhotoGallery(List<PhotoGallery> pathPhotoGallery) {
		this.pathPhotoGallery = pathPhotoGallery;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

}
