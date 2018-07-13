package ci.weget.web.entites;

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
@JsonSubTypes({ @Type(name = "ME", value = Membres.class), @Type(name = "AD", value = Administrateurs.class) })
public abstract class Personnes extends AbstractEntity {

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

	@Column(name = "TYPE_PERSONNE", insertable = false, updatable = false)
	private String type;

	@Embedded
	private Adresses adresse;
	@Column(unique = true)
	private String login;

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Type_Statut")
	private Type_Statut typeStratut;
	
	
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cvPersonnes")
	private CvPersonnes cvPersonnes;
    // cles etrangres
   /* @Column(name = "id_Entreprise",insertable = false, updatable = false)
	private long idEntreprise;
    @Column(name = "id_Type_Statut",insertable = false, updatable = false)
	private long idTypeStatut;
	@Column(name = "id_cvPersonnes",insertable = false, updatable = false)
	private long idCvPersonnes;*/
	

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_telephones")
	private List<Telephones> telephones;

	public Personnes() {
		super();

	}

	public Personnes(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
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

	public Personnes(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
		this.cni = cni;
		this.nomComplet = nomComplet;
		this.type = type;
	}

	public Personnes(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	
	public Personnes(String password, String repassword, String login) {
		super();
		this.password = password;
		this.repassword = repassword;
		this.login = login;
	}

	public Personnes(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresses adresse, List<Telephones> telephones) {
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

	/*public long getIdEntreprise() {
		return idEntreprise;
	}

	public long getIdTypeStatut() {
		return idTypeStatut;
	}

	public long getIdCvPersonnes() {
		return idCvPersonnes;
	}*/

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

	public Adresses getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresses adresse) {
		this.adresse = adresse;
	}

	public List<Telephones> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephones> telephones) {
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

	public Type_Statut getTypeStratut() {
		return typeStratut;
	}

	public void setTypeStratut(Type_Statut typeStratut) {
		this.typeStratut = typeStratut;
	}

	public CvPersonnes getCvPersonnes() {
		return cvPersonnes;
	}

	public void setCvPersonnes(CvPersonnes cvPersonnes) {
		this.cvPersonnes = cvPersonnes;
	}

	@Override
	public String toString() {
		return "Personnes [cni=" + cni + ", titre=" + titre + ", nom=" + nom + ", prenom=" + prenom + ", password="
				+ password + ", repassword=" + repassword + ", actived=" + actived + ", nomComplet=" + nomComplet
				+ ", pathPhoto=" + pathPhoto + ", type=" + type + ", adresse=" + adresse + ", login=" + login
				+ ", entreprise=" + entreprise + ", typeStratut=" + typeStratut + ", cvPersonnes=" + cvPersonnes
				+ ", telephones=" + telephones + "]";
	}

	
}
