package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.QuartierRepository;
import ci.weget.web.entites.combo.Quartier;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboQuartierMetierImpl implements IComboQuartierMetier{

	@Autowired
	private QuartierRepository quartierRepository;
	@Override
	public Quartier creer(Quartier entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return quartierRepository.save(entity);
	}

	@Override
	public Quartier modifier(Quartier entity) throws InvalideTogetException {
		
		return quartierRepository.save(entity);
	}

	@Override
	public List<Quartier> findAll() {
		// TODO Auto-generated method stub
		return quartierRepository.findAll();
	}

	@Override
	public Quartier findById(Long id) {
		// TODO Auto-generated method stub
		return quartierRepository.findQuartierParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		quartierRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Quartier> entites) {
		quartierRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		quartierRepository.existsById(id);
		return true;
	}

	

	@Override
	public List<Quartier> qartierParVille(Long id) {
		
		return quartierRepository.findAllQuartierParville(id);
	}

}
