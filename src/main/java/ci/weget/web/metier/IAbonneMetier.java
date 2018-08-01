package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface IAbonneMetier {
	List<Personne> touslesAbonnes();
    List<DetailBlock> lesAbonneParBlock(Long id);
    Personne findById(final Long id);
    Personne findAbonneByLogin(String login);
    Personne modifierAbonne(Personne p) throws InvalideTogetException;
}
