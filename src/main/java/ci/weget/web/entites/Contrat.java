package ci.weget.web.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_Contrat")
public class Contrat extends AbstractEntity {
	
	@ElementCollection
	private List<String>  dureeContrat = new ArrayList<String>();
	@ElementCollection
	private List<String> periodeContrat = new ArrayList<String>();
	@ElementCollection
	private List<String> disponibilite = new ArrayList<String>();
	public Contrat() {
		super();
		
	}

	public Contrat(List<String> dureeContrat, List<String> periodeContrat) {
		super();
		this.dureeContrat = dureeContrat;
		this.periodeContrat = periodeContrat;
	}

	public List<String> getDureeContrat() {
		return dureeContrat;
	}

	public void setDureeContrat(List<String> dureeContrat) {
		this.dureeContrat = dureeContrat;
	}

	public List<String> getPeriodeContrat() {
		return periodeContrat;
	}

	public void setPeriodeContrat(List<String> periodeContrat) {
		this.periodeContrat = periodeContrat;
	}

	public List<String> getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(List<String> disponibilite) {
		this.disponibilite = disponibilite;
	}

	
}
