package ci.weget.web.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Adresse implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String boitePostal;
	private String email;
	private String pays;
	private String ville;
	private String quartier;
	private String adresseGeographique;
	private String siteWeb;
	public String getBoitePostal() {
		return boitePostal;
	}
	public void setBoitePostal(String boitePostal) {
		this.boitePostal = boitePostal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}
	public String getAdresseGeographique() {
		return adresseGeographique;
	}
	public void setAdresseGeographique(String adresseGeographique) {
		this.adresseGeographique = adresseGeographique;
	}
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	
	
}
