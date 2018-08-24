package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_FORMATION")
public class Formation extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private String description;
	private String formation_niveau;
	private String contenu;
	private String dureeFormation;
	private String diplome;
	private String formation_prix;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;

	public Formation() {

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

	public String getFormation_niveau() {
		return formation_niveau;
	}

	public void setFormation_niveau(String formation_niveau) {
		this.formation_niveau = formation_niveau;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getDureeFormation() {
		return dureeFormation;
	}

	public void setDureeFormation(String dureeFormation) {
		this.dureeFormation = dureeFormation;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getFormation_prix() {
		return formation_prix;
	}

	public void setFormation_prix(String formation_prix) {
		this.formation_prix = formation_prix;
	}

	public SousBlock getSousBlock() {
		return sousBlock;
	}

	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}

}
