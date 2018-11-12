package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.FormationRepository;
import ci.weget.web.entites.Formation;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class FormationMetierImpl implements IFormationMetier{

	@Autowired
	private FormationRepository formationRepository;
	@Override
	public Formation creer(Formation entity) throws InvalideTogetException {
		
		return formationRepository.save(entity);
	}

	@Override
	public Formation modifier(Formation entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return formationRepository.save(entity);
	}

	@Override
	public List<Formation> findAll() {
		// TODO Auto-generated method stub
		return formationRepository.findAll();
	}

	@Override
	public Formation findById(Long id) {
		
		return formationRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		formationRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Formation> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Formation> getFormationParSousBlock(Long id) {
		
		return formationRepository.getFormationParSousBlock(id);
	}

}
