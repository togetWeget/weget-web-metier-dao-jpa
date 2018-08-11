package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Contrat")
public class Contrat extends AbstractEntity {
	private String dureeContrat;
	private String periodeContrat;
	
	
	public Contrat(String dureeContrat, String periodeContrat) {
		super();
		this.dureeContrat = dureeContrat;
		this.periodeContrat = periodeContrat;
	}
	public Contrat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDureeContrat() {
		return dureeContrat;
	}
	public void setDureeContrat(String dureeContrat) {
		this.dureeContrat = dureeContrat;
	}
	public String getPeriodeContrat() {
		return periodeContrat;
	}
	public void setPeriodeContrat(String periodeContrat) {
		this.periodeContrat = periodeContrat;
	}
	

}
