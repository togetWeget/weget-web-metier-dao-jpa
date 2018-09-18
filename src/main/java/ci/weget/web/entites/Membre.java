package ci.weget.web.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Membre")
@DiscriminatorValue("ME")
public class Membre extends Personne {

	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cvPersonne")
	private CvPersonne cvPersonne;
		
	private String description;

	public Membre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Membre(String login, String password, String repassword) {
		super(login, password, repassword);
		// TODO Auto-generated constructor stub
	}

	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto, String type,
			Adresse adresse, List<Telephone> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		// TODO Auto-generated constructor stub
	}

	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		// TODO Auto-generated constructor stub
	}

	public Membre(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		// TODO Auto-generated constructor stub
	}

	public Membre(String login, String password) {
		super(login, password);
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CvPersonne getCvPersonne() {
		return cvPersonne;
	}

	public void setCvPersonne(CvPersonne cvPersonne) {
		this.cvPersonne = cvPersonne;
	}

}
