package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Tarif;

public interface ITarifMetier extends Imetier<Tarif, Long>{
	List<Tarif> getTarifParBlockId(Long id);
	 Tarif  ajouterBlock(Tarif t, Block b);
}
