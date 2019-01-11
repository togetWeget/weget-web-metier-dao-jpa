package ci.weget.web.entites.actualite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_Actualite")
public class Actualite extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String titre;
	@Column(columnDefinition="TEXT")
	private String contenu;
	private String pathPhoto;
	
	
	public Actualite() {
		super();
		
	}
	
	public Actualite(String titre, String contenu) {
		super();
		this.titre = titre;
		this.contenu = contenu;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contenu == null) ? 0 : contenu.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		Actualite other = (Actualite) obj;
		if (contenu == null) {
			if (other.contenu != null)
				return false;
		} else if (!contenu.equals(other.contenu))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Actualite [titre=" + titre + ", contenu=" + contenu + "]";
	}
	
	

}
