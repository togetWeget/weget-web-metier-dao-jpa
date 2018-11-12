package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	private List<String> pathPhotoCouverture;
	private String pathLogo;
	@Embedded
	private Adresse adresse;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Telephones")
	private List<Telephone> telephones;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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
	private Long idBlock;

	public SousBlock() {
		super();

	}

	public SousBlock(String nom, String typeEtablissement, String refSousBlock, String presentation, String description,
			List<String> pathPhotoCouverture, String pathLogo, Adresse adresse, List<Telephone> telephones,
			DetailBlock detailBlock, List<Chiffre> chiffre, List<Partenaire> partenaire, List<Temoignage> temoignage,
			Long idBlock) {
		super();
		this.nom = nom;
		this.typeEtablissement = typeEtablissement;
		this.refSousBlock = refSousBlock;
		this.presentation = presentation;
		this.description = description;
		this.pathPhotoCouverture = pathPhotoCouverture;
		this.pathLogo = pathLogo;
		this.adresse = adresse;
		this.telephones = telephones;
		this.detailBlock = detailBlock;
		this.chiffre = chiffre;
		this.partenaire = partenaire;
		this.temoignage = temoignage;
		this.idBlock = idBlock;
	}

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

	public Long getIdBlock() {
		return idBlock;
	}

	public void setIdBlock(Long idBlock) {
		this.idBlock = idBlock;
	}

	public List<String> getPathPhotoCouverture() {
		return pathPhotoCouverture;
	}

	public void setPathPhotoCouverture(List<String> pathPhotoCouverture) {
		this.pathPhotoCouverture = pathPhotoCouverture;
	}

	public void setDetailBlock(DetailBlock detailBlock) {
		this.detailBlock = detailBlock;
	}

	@Override
	public String toString() {
		return "SousBlock [nom=" + nom + ", typeEtablissement=" + typeEtablissement + ", refSousBlock=" + refSousBlock
				+ ", presentation=" + presentation + ", description=" + description + ", pathPhotoCouverture="
				+ pathPhotoCouverture + ", pathLogo=" + pathLogo + ", adresse=" + adresse + ", telephones=" + telephones
				+ ", detailBlock=" + detailBlock + ", chiffre=" + chiffre + ", partenaire=" + partenaire
				+ ", temoignage=" + temoignage + ", idBlock=" + idBlock + "]";
	}

}
