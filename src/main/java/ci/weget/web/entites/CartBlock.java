package ci.weget.web.entites;

public class CartBlock {
	
    private Tarif tarif;
    private Integer quantity;
    
    
    public CartBlock() {
		super();
		
	}

	public CartBlock(Tarif tarif, Integer quantity) {
		super();
		this.tarif = tarif;
		this.quantity = quantity;
	}

	public Float getSubTotal() {
        return  tarif.getMontant()* quantity;
    }

	public Tarif getTarif() {
		return tarif;
	}

	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
