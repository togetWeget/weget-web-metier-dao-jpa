package ci.weget.web.entites;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_COURS")
public class Cours extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String titre;
	private String contenu;
	private LocalDate dateCours;
	private LocalDate dureeCours;
	private String description;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Formation id_formation;
	@Column(name = "id_formation", insertable = false, updatable = false)
	private long idformation;

	public Cours() {
		super();
	}

	public Cours(String titre, String contenu, LocalDate dateCours, LocalDate dureeCours, String description) {
		super();
		this.titre = titre;
		this.contenu = contenu;
		this.dateCours = dateCours;
		this.dureeCours = dureeCours;
		this.description = description;
	}

	public LocalDate getDateCours() {
		return dateCours;
	}

	public void setDateCours(LocalDate dateCours) {
		this.dateCours = dateCours;
	}

	public Formation getId_formation() {
		return id_formation;
	}

	public long getIdformation() {
		return idformation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

}
