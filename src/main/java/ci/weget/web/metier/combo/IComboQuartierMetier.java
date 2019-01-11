package ci.weget.web.metier.combo;

import java.util.List;

import ci.weget.web.entites.combo.Quartier;
import ci.weget.web.metier.Imetier;

public interface IComboQuartierMetier extends Imetier<Quartier, Long>{
  List<Quartier>  qartierParVille(Long id);
}
