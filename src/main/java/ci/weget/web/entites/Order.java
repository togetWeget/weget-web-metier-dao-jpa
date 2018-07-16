package ci.weget.web.entites;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "T_Commande")
public class Order extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private LocalDateTime orderDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Personne")
	private Personnes personne;
	@Embedded
	private CreditCard creditCard = new CreditCard();
/*
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<OrderLine> orderLines;
*/
	public Order(Personnes personne, CreditCard creditCard) {
		super();
		this.personne = personne;
		this.creditCard = creditCard;
	}

	/*public Order(LocalDateTime orderDate, Personnes personne, CreditCard creditCard, List<OrderLine> orderLines) {
		super();
		this.orderDate = orderDate;
		this.personne = personne;
		this.creditCard = creditCard;
		this.orderLines = orderLines;
	}
*/
	@PrePersist
	private void setDefaultData() {
		orderDate = LocalDateTime.now();
	}

	/*public Float getTotal() {
		if (orderLines == null || orderLines.isEmpty())
			return 0f;
		Float total = 0f;
		for (OrderLine orderLine : orderLines) {
			total += (orderLine.getSubTotal());
		}
		return total;
	}
*/
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Personnes getPersonne() {
		return personne;
	}

	public void setPersonne(Personnes personne) {
		this.personne = personne;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

/*	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	*/
}
