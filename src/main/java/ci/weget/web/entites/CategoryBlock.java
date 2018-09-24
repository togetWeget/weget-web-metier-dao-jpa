package ci.weget.web.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_CategoryBlock")
public class CategoryBlock extends AbstractEntity{

private static final long serialVersionUID = 1L;
private String libelle;
private String description;
private String phathphoto;

@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinColumn(name = "id_Block")
private Block block;

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

public String getPhathphoto() {
	return phathphoto;
}

public void setPhathphoto(String phathphoto) {
	this.phathphoto = phathphoto;
}

public Block getBlock() {
	return block;
}

public void setBlock(Block block) {
	this.block = block;
}



}
