package ci.weget.web.metier;

import ci.weget.web.entites.Espaces;
import ci.weget.web.entites.Personnes;





public interface IEspaceMetier extends Imetier<Espaces, Long> {
	public Boolean ajoutEspaceToPersonne(Personnes personnes, double prix);

	
}
