package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.PaysRepository;
import ci.weget.web.entites.combo.Pays;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboPaysMetierImpl implements IComboPaysMetier {
	@Autowired
	private PaysRepository paysRepository;
	
	@Override
	public Pays creer(Pays entity) throws InvalideTogetException {
		
		return paysRepository.save(entity);
	}

	@Override
	public Pays modifier(Pays entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return paysRepository.save(entity);
	}

	@Override
	public List<Pays> findAll() {
		// TODO Auto-generated method stub
		return paysRepository.findAll();
	}

	@Override
	public Pays findById(Long id) {
		
		return paysRepository.findPaysParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		paysRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Pays> entites) {
		paysRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		paysRepository.existsById(id);
		return true;
	}

}
