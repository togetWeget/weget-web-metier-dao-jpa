package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.DomaineRepository;
import ci.weget.web.entites.combo.Domaine;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DomaineMetierImpl implements IDomaineMetier {
@Autowired
private DomaineRepository domaineRepository;
	@Override
	public Domaine creer(Domaine entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return domaineRepository.save(entity);
	}

	@Override
	public Domaine modifier(Domaine entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return domaineRepository.save(entity);
	}

	@Override
	public List<Domaine> findAll() {
		// TODO Auto-generated method stub
		return domaineRepository.findAll();
	}

	@Override
	public Domaine findById(Long id) {
		// TODO Auto-generated method stub
		return domaineRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		domaineRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Domaine> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
