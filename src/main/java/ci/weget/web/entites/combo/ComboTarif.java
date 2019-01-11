package ci.weget.web.entites.combo;

import javax.persistence.Entity;
import javax.persistence.Table;

import ci.weget.web.entites.AbstractEntity;

@Entity
@Table(name = "T_ComboTarif")
public class ComboTarif extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private int dureeTarif;
	private String typeDuree;
	public ComboTarif() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComboTarif(int dureeTarif, String typeDuree) {
		super();
		this.dureeTarif = dureeTarif;
		this.typeDuree = typeDuree;
	}
	public int getDureeTarif() {
		return dureeTarif;
	}
	public void setDureeTarif(int dureeTarif) {
		this.dureeTarif = dureeTarif;
	}
	public String getTypeDuree() {
		return typeDuree;
	}
	public void setTypeDuree(String typeDuree) {
		this.typeDuree = typeDuree;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + dureeTarif;
		result = prime * result + ((typeDuree == null) ? 0 : typeDuree.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComboTarif other = (ComboTarif) obj;
		if (dureeTarif != other.dureeTarif)
			return false;
		if (typeDuree == null) {
			if (other.typeDuree != null)
				return false;
		} else if (!typeDuree.equals(other.typeDuree))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ComboTarif [dureeTarif=" + dureeTarif + ", typeDuree=" + typeDuree + "]";
	}
	
}
