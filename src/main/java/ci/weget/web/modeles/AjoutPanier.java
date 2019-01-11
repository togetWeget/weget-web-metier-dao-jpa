package ci.weget.web.modeles;

import java.time.LocalDateTime;
import java.util.List;

public class AjoutPanier {

	private LocalDateTime date;
	private Double quantite;
	private Double total;
	private long idMembre;
	private long idTarif;


	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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

}
