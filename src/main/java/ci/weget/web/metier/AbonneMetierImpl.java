package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class AbonneMetierImpl implements IAbonneMetier {

	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private DetailBlocksRepository detailBlocksRepository;

	@Override
	public List<Personne> touslesAbonnes() {

		return personnesRepository.getAllAbonnes();
	}

	@Override
	public List<DetailBlock> lesAbonneParBlock(Long id) {
		
		return detailBlocksRepository.findAllAbonneParBloc(id);
	}
	@Override
	public Personne findById(Long id) {

		return personnesRepository.getPersonneByid(id);
	}

	@Override
	public Personne findAbonneByLogin(String login) {
		
		return personnesRepository.findByLogin(login);
	}

	@Override
	public Personne modifierAbonne(Personne pers) throws InvalideTogetException {
		Personne p = personnesRepository.findById(pers.getId()).get();

		if (p != null && p.getId() != pers.getId()) {

			throw new InvalideTogetException("cette personne est deja une autre personne");

		}

		return personnesRepository.save(pers);
	}

}
