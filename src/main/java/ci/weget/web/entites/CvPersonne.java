package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_CvPersonne")
public class CvPersonne extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private String diplome;
	private String specialite;
	private String anneExperience;
	private String motivation;
	private String fonctionActuelle;
	private String domaine;
	@ElementCollection
	private List<String> autreSpecialite = new ArrayList<String>();
	private String description;
	private String pathCv;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cvPersonne")
	private List<Experience> experience;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cvPersonne")
	private List<CursusColaire> cursus;
	@ElementCollection
	private List<String> dureeContrat = new ArrayList<>();

	@ElementCollection
	private List<String> periodeContrat = new ArrayList<>();
	@ElementCollection
	private List<String> disponibilite = new ArrayList<>();

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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getFonctionActuelle() {
		return fonctionActuelle;
	}

	public void setFonctionActuelle(String fonctionActuelle) {
		this.fonctionActuelle = fonctionActuelle;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public List<String> getAutreSpecialite() {
		return autreSpecialite;
	}

	public void setAutreSpecialite(List<String> autreSpecialite) {
		this.autreSpecialite = autreSpecialite;
	}

	public String getPathCv() {
		return pathCv;
	}

	public void setPathCv(String pathCv) {
		this.pathCv = pathCv;
	}

	public List<Experience> getExperience() {
		return experience;
	}

	public void setExperience(List<Experience> experience) {
		this.experience = experience;
	}

	public List<CursusColaire> getCursus() {
		return cursus;
	}

	public void setCursus(List<CursusColaire> cursus) {
		this.cursus = cursus;
	}

	public List<String> getDureeContrat() {
		return dureeContrat;
	}

	public void setDureeContrat(List<String> dureeContrat) {
		this.dureeContrat = dureeContrat;
	}

	public List<String> getPeriodeContrat() {
		return periodeContrat;
	}

	public void setPeriodeContrat(List<String> periodeContrat) {
		this.periodeContrat = periodeContrat;
	}

	public List<String> getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(List<String> disponibilite) {
		this.disponibilite = disponibilite;
	}

}
