package ci.weget.web.entites;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_PhotoGallery")
public class PhotoGallery extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private String titre;
	private String description;
	private LocalDate date;
	private String pathPhotoGallery;
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getPathPhotoGallery() {
		return pathPhotoGallery;
	}
	public void setPathPhotoGallery(String pathPhotoGallery) {
		this.pathPhotoGallery = pathPhotoGallery;
	}
	
}   
