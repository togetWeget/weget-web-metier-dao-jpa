package ci.weget.web.entites.immobilier;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name="T_Immobilier")
public class Immobilier extends AbstractEntity {

	private static final long serialVersionUID = 1L;
    private String nom;
    private String description;
    @ElementCollection
    private List<String> pathImage;
	public Immobilier() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Immobilier(String nom, String description, List<String> pathImage) {
		super();
		this.nom = nom;
		this.description = description;
		this.pathImage = pathImage;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getPathImage() {
		return pathImage;
	}
	public void setPathImage(List<String> pathImage) {
		this.pathImage = pathImage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((pathImage == null) ? 0 : pathImage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Immobilier other = (Immobilier) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (pathImage == null) {
			if (other.pathImage != null)
				return false;
		} else if (!pathImage.equals(other.pathImage))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Immobilier [nom=" + nom + ", description=" + description + ", pathImage=" + pathImage + "]";
	}
    
   
}
