package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_Sous_Block")
public class SousBlock extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String nom;
	private String typeEtablissement;
	private String refSousBlock;
	private String presentation;
	private String description;
	@ElementCollection
	private List<String> pathPhoto = new ArrayList<>();
	private String pathLogo;
	@Embedded
	private Adresse adresse;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Telephones")
	private List<Telephone> telephones;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_detailBlock")
	private DetailBlock detailBlock;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SouBlock")
	private List<Chiffre> chiffre;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SouBlock")
	private List<Partenaire> partenaire;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SouBlock")
	private List<Temoignage> temoignage;

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefSousBlock() {
		return refSousBlock;
	}

	public void setRefSousBlock(String refSousBlock) {
		this.refSousBlock = refSousBlock;
	}

	public DetailBlock getDetailBlock() {
		return detailBlock;
	}

	public void setDetailBlock(DetailBlock detailBlock) {
		this.detailBlock = detailBlock;
	}

	public List<String> getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(List<String> pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTypeEtablissement() {
		return typeEtablissement;
	}

	public void setTypeEtablissement(String typeEtablissement) {
		this.typeEtablissement = typeEtablissement;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public String getPathLogo() {
		return pathLogo;
	}

	public void setPathLogo(String pathLogo) {
		this.pathLogo = pathLogo;
	}

	public List<Chiffre> getChiffre() {
		return chiffre;
	}

	public void setChiffre(List<Chiffre> chiffre) {
		this.chiffre = chiffre;
	}

	public List<Partenaire> getPartenaire() {
		return partenaire;
	}

	public void setPartenaire(List<Partenaire> partenaire) {
		this.partenaire = partenaire;
	}

	public List<Temoignage> getTemoignage() {
		return temoignage;
	}

	public void setTemoignage(List<Temoignage> temoignage) {
		this.temoignage = temoignage;
	}

}
