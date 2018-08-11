package ci.weget.web.metier;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

public interface IDetailBlocksMetier extends Imetier<DetailBlock, Long> {
	DetailBlock ajoutdetailBlocks(Personne personne,Block block);
	public void addMembresToBlocks(String statut, String blockName);
	
}
