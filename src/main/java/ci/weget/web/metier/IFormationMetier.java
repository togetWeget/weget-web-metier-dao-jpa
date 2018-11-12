package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Formation;

public interface IFormationMetier extends Imetier<Formation, Long> {
List<Formation>	getFormationParSousBlock(Long id);
}
