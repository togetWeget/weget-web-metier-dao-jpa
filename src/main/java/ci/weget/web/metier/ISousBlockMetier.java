package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.SousBlock;

public interface ISousBlockMetier extends Imetier<SousBlock, Long> {
public SousBlock rechercheSousBlockParLibelle(String libelle);
List<SousBlock> chercherSousBlockParMc(String mc);

}
