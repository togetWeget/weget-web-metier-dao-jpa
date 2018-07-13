package ci.weget.web.entites;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ValidationException;

public class OrderLine extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private Integer quantity;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_Tarif")
	private Tarif tarif;

	public OrderLine() {
		super();
		
	}

	public OrderLine(Integer quantity, Tarif tarif) {
		super();
		this.quantity = quantity;
		this.tarif = tarif;
	}

	@PrePersist
	@PreUpdate
	private void validateData() {
		if (quantity == null || quantity < 0)
			throw new ValidationException("Invalid quantity");
	}

	public Float getSubTotal() {
		return tarif.getMontant() * quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Tarif getTarif() {
		return tarif;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}
	
}
