package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_Cour")
public class Cour extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
    private String titre;
    private String description;
	public Cour() {
		super();
	}
	public Cour(String titre, String description) {
		super();
		this.titre = titre;
		this.description = description;
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
    
}
