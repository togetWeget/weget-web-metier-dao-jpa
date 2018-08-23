package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

public interface IBlocksMetier extends Imetier<Block, Long> {
Block rechercheParLibelle(String libelle);
List<Block> chercherBlockParMc(String mc);
List<Personne> getPersonnes(Long id);

}
