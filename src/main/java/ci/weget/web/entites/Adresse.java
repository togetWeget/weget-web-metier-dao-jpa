package ci.weget.web.entites;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String codePostal;
	private String quartier;
	private String ville;
	private String pays;
	private String email;
	
    public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	}
