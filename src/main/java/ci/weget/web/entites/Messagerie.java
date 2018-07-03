package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_Messageries")
public class Messagerie extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
     private String contenu;
     
     @ManyToOne(fetch= FetchType.LAZY)
     @JoinColumn(name = "id_Personne")
     private Personnes personnes;
     @Column(name = "id_personnes", insertable = false, updatable = false)
 	private long idPersonnes;
	public Messagerie() {
		super();
		
	}
	public Messagerie(String contenu) {
		super();
		this.contenu = contenu;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	@Override
	public String toString() {
		return "Messagerie [contenu=" + contenu + "]";
	}
     
     
}
