package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_Experience")
public class Experience extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String postOccupe;
	private String entreprise;
	private String periode;
	private String tache;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Membre")
	private Membre membre;
	
	
	public String getPostOccupe() {
		return postOccupe;
	}
	public void setPostOccupe(String postOccupe) {
		this.postOccupe = postOccupe;
	}
	public String getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public String getTache() {
		return tache;
	}
	public void setTache(String tache) {
		this.tache = tache;
	}
	
	
	
}
