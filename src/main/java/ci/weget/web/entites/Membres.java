package ci.weget.web.entites;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "T_Membre")
@DiscriminatorValue("ME")
public class Membres extends Personnes {
	
	private static final long serialVersionUID = 1L;
	
    private String description;
    
   
    
	public Membres() {
		super();
		// TODO Auto-generated constructor stub
	}
	
public Membres(String password, String repassword, String login) {
		super(password, repassword, login);
		// TODO Auto-generated constructor stub
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
	
	
	public Membres(String titre, String nom, String prenom, String cni, String nomComplet, String type) {
		super(titre, nom, prenom, cni, nomComplet, type);
		// TODO Auto-generated constructor stub
	}

	public Membres(String login, String password) {
		super(login, password);
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
   }
