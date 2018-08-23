package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

public interface IDetailBlocksMetier extends Imetier<DetailBlock, Long> {
	DetailBlock ajoutdetailBlocks(Personne personne,Block block);
	public void addMembresToBlocks(String statut, String blockName);
	public List<DetailBlock> personneALLBlock(long id);
	
}
