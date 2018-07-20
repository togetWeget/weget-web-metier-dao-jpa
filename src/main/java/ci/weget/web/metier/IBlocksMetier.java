package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Personnes;

public interface IBlocksMetier extends Imetier<Blocks, Long> {
Blocks rechercheParLibelle(String libelle);
List<Blocks> chercherBlockParMc(String mc);
List<Personnes> getPersonnes(Long id);
List<Personnes>getPersonnesParBlockLibelle(String libelle);
}
