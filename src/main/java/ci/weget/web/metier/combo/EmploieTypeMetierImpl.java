package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.EmploieTypeRepository;
import ci.weget.web.entites.combo.EmploieType;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class EmploieTypeMetierImpl implements IEmploieTypeMetier {
@Autowired
private EmploieTypeRepository emploieTypeRepository;
	@Override
	public EmploieType creer(EmploieType entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return emploieTypeRepository.save(entity);
	}

	@Override
	public EmploieType modifier(EmploieType entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return emploieTypeRepository.save(entity);
	}

	@Override
	public List<EmploieType> findAll() {
		// TODO Auto-generated method stub
		return emploieTypeRepository.findAll();
	}

	@Override
	public EmploieType findById(Long id) {
		// TODO Auto-generated method stub
		return emploieTypeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		emploieTypeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<EmploieType> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
