package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_DocumentCompetence")
public class DocCompetence extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private String titre;
	private String description;
   
	private String pathDocument;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Membre")
	private Membre membre;

	public DocCompetence() {
		super();
		
	}

	public DocCompetence(String titre, String description, String pathDocument, Membre membre) {
		super();
		this.titre = titre;
		this.description = description;
		this.pathDocument = pathDocument;
		this.membre = membre;
	}

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

	public String getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(String pathDocument) {
		this.pathDocument = pathDocument;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	
	
}
