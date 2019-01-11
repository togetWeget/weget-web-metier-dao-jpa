package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.FonctionRepository;
import ci.weget.web.entites.combo.Fonction;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class FonctionMetierImpl implements IFonctionMetier{
@Autowired
private FonctionRepository fonctionRepository;
	@Override
	public Fonction creer(Fonction entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return fonctionRepository.save(entity);
	}

	@Override
	public Fonction modifier(Fonction entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return fonctionRepository.save(entity);
	}

	@Override
	public List<Fonction> findAll() {
		// TODO Auto-generated method stub
		return fonctionRepository.findAll();
	}

	@Override
	public Fonction findById(Long id) {
		// TODO Auto-generated method stub
		return fonctionRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		fonctionRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Fonction> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
