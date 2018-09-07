package ci.weget.web.entites;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "T_Personnes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_PERSONNE", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(name = "ME", value = Membre.class), @Type(name = "AD", value = Administrateur.class) })
public abstract class Personne extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String cni;
	private String titre;
	private String nom;
	private String prenom;
	private String password;
	private String repassword;
	private boolean actived;
	private String nomComplet;
	private String pathPhoto;
	private String pathPhotoCouveture;
	private Double nombreVue;
	private String groupSanguin;
	private LocalDate dateNaissance;
	private String genre;
	@Column(name = "TYPE_PERSONNE", insertable = false, updatable = false)
	private String type;

	@Embedded
	private Adresse adresse;

	@Column(unique = true)
	private String login;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cvPersonne")
	private CvPersonne cvPersonne;
	// cles etrangres
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Personne")
	private List<Telephone> telephones;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Langue")
	private List<LangueParle> langues;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_TypeStatut")
	private TypeStatut typestatut;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Contrat")
	private Contrat contrat;
	private String couleur;


	/*@Column(name = "id_Entreprise", insertable = false, updatable = false)
	private long idEntreprise;

	@Column(name = "id_cvPersonne", insertable = false, updatable = false)
	private long idCvPersonne;

	@Column(name = "id_TypeStatut", insertable = false, updatable = false)
	private long idTypeStatut;

	@Column(name = "id_Contrat", insertable = false, updatable = false)
	private long idContrat;
*/
	public Personne() {
		super();

	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.pathPhoto = pathPhoto;
		this.type = type;

	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.type = type;
	}

	public Personne(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public Personne(String password, String repassword, String login) {
		super();
		this.password = password;
		this.repassword = repassword;
		this.login = login;
	}

	public Personne(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresse adresse, List<Telephone> telephones) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.pathPhoto = pathPhoto;
		this.type = type;
		this.adresse = adresse;
		this.telephones = telephones;
	}

	
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/*public long getIdEntreprise() {
		return idEntreprise;
	}

	public long getIdCvPersonne() {
		return idCvPersonne;
	}

	public long getIdTypeStatut() {
		return idTypeStatut;
	}

	public long getIdContrat() {
		return idContrat;
	}
*/
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCni() {
		return cni;
	}

	public void setCni(String cni) {
		this.cni = cni;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	@PrePersist
	@PreUpdate
	public void setNomComplet() {
		this.nomComplet = nom + " " + prenom;
	}
	
	public Double getNombreVue() {
		return nombreVue;
	}

	public void setNombreVue(Double nombreVue) {
		this.nombreVue = nombreVue;
	}

	public TypeStatut getTypestatut() {
		return typestatut;
	}

	public void setTypestatut(TypeStatut typestatut) {
		this.typestatut = typestatut;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getRepassword() {
		return repassword;
	}

	@JsonSetter
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public void setCvPersonne(CvPersonne cvPersonne) {
		this.cvPersonne = cvPersonne;
	}

	public CvPersonne getCvPersonne() {
		return cvPersonne;
	}

	
	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public String getGroupSanguin() {
		return groupSanguin;
	}

	public void setGroupSanguin(String groupSanguin) {
		this.groupSanguin = groupSanguin;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<LangueParle> getLangues() {
		return langues;
	}

	public void setLangues(List<LangueParle> langues) {
		this.langues = langues;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getPathPhotoCouveture() {
		return pathPhotoCouveture;
	}

	public void setPathPhotoCouveture(String pathPhotoCouveture) {
		this.pathPhotoCouveture = pathPhotoCouveture;
	}

	@Override
	public String toString() {
		return "Personnes [cni=" + cni + ", titre=" + titre + ", nom=" + nom + ", prenom=" + prenom + ", password="
				+ password + ", repassword=" + repassword + ", actived=" + actived + ", nomComplet=" + nomComplet
				+ ", pathPhoto=" + pathPhoto + ", type=" + type + ", adresse=" + adresse + ", login=" + login
				+ ", entreprise=" + entreprise + ", cvPersonnes=" + cvPersonne + ", telephones=" + telephones + "]";
	}

}
