package ci.weget.web.entites;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_Type_Paiement")
public class Type_Paiement extends AbstractEntity{
private String libelle;
private String description;
public String getLibelle() {
	return libelle;
}
public void setLibelle(String libelle) {
	this.libelle = libelle;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}



}
