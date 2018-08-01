package ci.weget.web.entites;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Administration")
@DiscriminatorValue("AD")
public class Administrateur extends Personne {

	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Id_AdminSup")
    private Administrateur adminSup;
	public Administrateur() {
		super();
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresse adresse, List<Telephone> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		
	}

	public Administrateur(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		
	}

	public Administrateur(String login, String password, String type) {
		super(login, password, type);
		
	}

	public Administrateur(String login, String password) {
		super(login, password);
		
	}

}
