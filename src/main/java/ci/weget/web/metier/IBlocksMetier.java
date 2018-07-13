package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Blocks;

public interface IBlocksMetier extends Imetier<Blocks, Long> {
Blocks rechercheParLibelle(String libelle);
List<Blocks> chercherBlockParMc(String mc);
}
