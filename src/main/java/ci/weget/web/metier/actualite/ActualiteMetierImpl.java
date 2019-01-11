package ci.weget.web.metier.actualite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ci.weget.web.dao.actualite.ActualiteRepository;
import ci.weget.web.entites.actualite.Actualite;
import ci.weget.web.exception.InvalideTogetException;

public class ActualiteMetierImpl implements IActualiteMetier {
	@Autowired
	ActualiteRepository actualiteRepository;

	@Override
	public Actualite creer(Actualite entity) throws InvalideTogetException {

		return actualiteRepository.save(entity);
	}

	@Override
	public Actualite modifier(Actualite entity) throws InvalideTogetException {
		
		return actualiteRepository.save(entity);
	}

	@Override
	public List<Actualite> findAll() {
		
		return actualiteRepository.findAll();
	}

	@Override
	public Actualite findById(Long id) {
		
		return actualiteRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		actualiteRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Actualite> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
