package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface IDetailBlocksMetier extends Imetier<DetailBlock, Long> {

	public List<DetailBlock> personneALLBlock(long id);

	public List<DetailBlock> detailBlocksPersonneParId(Long id);

	public List<DetailBlock> detailBlocksPersonneParLogin(String login);

	public List<DetailBlock> tousLesDetailBlock();

	public boolean creerAbonne(String login, String libelle);

	public boolean creerAbonneSpecial(String login, String libelle);

	public List<DetailBlock> abonneSpecial(DetailBlock p);

	public void addPersonneToBlocks(String login, String libelle) throws InvalideTogetException;

	public DetailBlock modifierVue(Long idPersonne, Long idBlock) throws InvalideTogetException;
}
