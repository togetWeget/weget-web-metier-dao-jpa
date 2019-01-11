package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.LangueRepository;
import ci.weget.web.entites.combo.Langue;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class LangueMetierImpl implements ILangueMetier {
@Autowired 
LangueRepository langueRepository;
	@Override
	public Langue creer(Langue entity) throws InvalideTogetException {
		
		return langueRepository.save(entity);
	}

	@Override
	public Langue modifier(Langue entity) throws InvalideTogetException {
		
		return langueRepository.save(entity);
	}

	@Override
	public List<Langue> findAll() {
		
		return langueRepository.findAll();
	}

	@Override
	public Langue findById(Long id) {
		
		return langueRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
	langueRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Langue> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
