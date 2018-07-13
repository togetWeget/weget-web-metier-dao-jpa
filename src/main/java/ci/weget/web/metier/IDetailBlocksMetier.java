package ci.weget.web.metier;

import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Membres;

public interface IDetailBlocksMetier extends Imetier<DetailBlocks, Long> {
	public Membres saveMembres(Membres membre);
	public DetailBlocks saveDetailBlocks(DetailBlocks db);
	public void addMembresToBlocks(String statut, String blockName);
}
