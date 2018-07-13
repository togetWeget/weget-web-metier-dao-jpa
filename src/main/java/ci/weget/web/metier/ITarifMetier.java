package ci.weget.web.metier;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Tarif;

public interface ITarifMetier extends Imetier<Tarif, Long> {
	Blocks getTarifBlockId(Long id);
}
