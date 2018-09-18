package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.SousBlock;

public interface ISousBlockMetier extends Imetier<SousBlock, Long> {
public SousBlock rechercheSousBlockParId(Long id);
List<SousBlock> chercherSousBlockParMc(String mc);
List<SousBlock> findSousBlockParIdBlock(Long id);
}
