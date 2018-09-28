package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_Position")
public class Position extends AbstractEntity {

	private static final long serialVersionUID = 1L;
    private Double longitude;
    private Double latitude;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_Membre")
    private Membre membre;
	public Position() {
		super();
		
	}
	public Position(Double longitude, Double latitude, Membre membre) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.membre = membre;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
    
}
