package ci.weget.web.modeles;

public class AjoutPanier {
	
	private long idBlock;
	private String date;
	private long idMembre;
	private Double quantite;
	private long idTarif;
	private Double total;
	
	
	public long getIdBlock() {
		return idBlock;
	}
	public void setIdBlock(long idBlock) {
		this.idBlock = idBlock;
	}
	public long getIdMembre() {
		return idMembre;
	}
	public void setIdMembre(long idMembre) {
		this.idMembre = idMembre;
	}
	public long getIdTarif() {
		return idTarif;
	}
	public void setIdTarif(long idTarif) {
		this.idTarif = idTarif;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getQuantite() {
		return quantite;
	}
	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
