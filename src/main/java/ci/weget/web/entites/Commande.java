package ci.weget.web.entites;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "T_Commande")
public class Commande extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private LocalDateTime dateCommande;
	private Panier panier;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personne personne;
	@Embedded
	private CreditCard creditCard = new CreditCard();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Panier> orderLines;

	public Commande() {
		super();

	}

	public Commande(Personne personne, CreditCard creditCard) {
		super();
		this.personne = personne;
		this.creditCard = creditCard;
	}

	@PrePersist
	@PreUpdate
	private void setDefaultData() {
		dateCommande = LocalDateTime.now();
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public LocalDateTime getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(LocalDateTime dateCommande) {
		this.dateCommande = dateCommande;
	}

}
