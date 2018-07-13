package ci.weget.web.entites;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Administration")
@DiscriminatorValue("AD")
public class Administrateurs extends Personnes {

	
	private static final long serialVersionUID = 1L;

	public Administrateurs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrateurs(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type, Adresses adresse, List<Telephones> telephones) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type, adresse, telephones);
		// TODO Auto-generated constructor stub
	}

	public Administrateurs(String titre, String nom, String prenom, String cni, String nomComplet, String pathPhoto,
			String type) {
		super(titre, nom, prenom, cni, nomComplet, pathPhoto, type);
		// TODO Auto-generated constructor stub
	}

	public Administrateurs(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		// TODO Auto-generated constructor stub
	}

	public Administrateurs(String login, String password, String type) {
		super(login, password, type);
		// TODO Auto-generated constructor stub
	}

	public Administrateurs(String login, String password) {
		super(login, password);
		// TODO Auto-generated constructor stub
	}

}
