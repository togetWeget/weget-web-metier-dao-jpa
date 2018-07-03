package ci.weget.web.entites;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "T_MEMBRE")
@DiscriminatorValue("ME")
public class Membres extends Personnes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomSociete;
    private String description;
    private String  diplome;
    private String anneExperience;
    private String statut;
    private String specialite;
	public Membres() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Membres(String nomSociete, String description, String diplome, String anneExperience, String statut,
			String specialite) {
		super();
		this.nomSociete = nomSociete;
		this.description = description;
		this.diplome = diplome;
		this.anneExperience = anneExperience;
		this.statut = statut;
		this.specialite = specialite;
	}

	public Membres(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresses adresse, List<Telephones> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		// TODO Auto-generated constructor stub
	}
	public Membres(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		// TODO Auto-generated constructor stub
	}
	
	public Membres(String login, String password, String type) {
		super(login, password, type);
		// TODO Auto-generated constructor stub
	}
	public Membres(String login, String password) {
		super(login, password);
		// TODO Auto-generated constructor stub
	}
	public String getNomSociete() {
		return nomSociete;
	}
	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getAnneExperience() {
		return anneExperience;
	}
	public void setAnneExperience(String anneExperience) {
		this.anneExperience = anneExperience;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
    
    
}
