package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_CvPersonne")
public class CvPersonne extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	
   
    private String  diplome;
    private String specialite;
    private String anneExperience;
    private String description;
    
   @ElementCollection
    private List<String> langues = new ArrayList<>();
    
	public CvPersonne() {
		super();
		
	}
	
	public CvPersonne(String diplome, String specialite, String anneExperience, String description) {
		super();
		this.diplome = diplome;
		this.specialite = specialite;
		this.anneExperience = anneExperience;
		this.description = description;
	}

	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public String getAnneExperience() {
		return anneExperience;
	}
	public void setAnneExperience(String anneExperience) {
		this.anneExperience = anneExperience;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getLangues() {
		return langues;
	}

	public void setLangues(List<String> langues) {
		this.langues = langues;
	}
    
    
}