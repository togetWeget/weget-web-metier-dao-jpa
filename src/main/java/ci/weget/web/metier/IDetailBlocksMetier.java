package ci.weget.web.metier;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Personnes;

public interface IDetailBlocksMetier extends Imetier<DetailBlocks, Long> {
	DetailBlocks ajoutdetailBlocks(Blocks block, Personnes personne);
	public void addMembresToBlocks(String statut, String blockName);
	
}
