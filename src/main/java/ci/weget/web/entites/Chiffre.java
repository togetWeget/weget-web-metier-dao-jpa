package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Chiffre")
public class Chiffre extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	private String titre;
	private String chiffre;
	private String description;
	/*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_SousBlock")
	private SousBlock sousBlock;*/

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getChiffre() {
		return chiffre;
	}

	public void setChiffre(String chiffre) {
		this.chiffre = chiffre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public SousBlock getSousBlock() {
		return sousBlock;
	}

	public void setSousBlock(SousBlock sousBlock) {
		this.sousBlock = sousBlock;
	}*/

}
