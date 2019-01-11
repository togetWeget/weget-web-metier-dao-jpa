package ci.weget.web.metier.combo;

import java.util.List;

import ci.weget.web.entites.combo.Ville;
import ci.weget.web.metier.Imetier;

public interface IComboVilleMetier extends Imetier<Ville, Long>{
List<Ville>	findAllVilleParPays(Long id);
}
