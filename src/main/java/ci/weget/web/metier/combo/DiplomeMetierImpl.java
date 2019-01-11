package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.DiplomeRepository;
import ci.weget.web.entites.combo.Diplome;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DiplomeMetierImpl implements IComboDiplomeMetier {
@Autowired
DiplomeRepository diplomeRepository;
	

@Override
	public Diplome creer(Diplome entity) throws InvalideTogetException {
	
		return diplomeRepository.save(entity);
	}

	@Override
	public Diplome modifier(Diplome entity) throws InvalideTogetException {
		
		return diplomeRepository.save(entity);
	}

	@Override
	public List<Diplome> findAll() {
	
		return diplomeRepository.findAll();
	}

	@Override
	public Diplome findById(Long id) {
		
		return diplomeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		diplomeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Diplome> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
