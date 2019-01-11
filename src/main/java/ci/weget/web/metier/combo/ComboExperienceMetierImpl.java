package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.ComboExperienceRepository;
import ci.weget.web.entites.combo.ComboExperience;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboExperienceMetierImpl implements IComboExperienceMetier {
@Autowired
private ComboExperienceRepository comboExperienceRepository;
	@Override
	public ComboExperience creer(ComboExperience entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return comboExperienceRepository.save(entity);
	}

	@Override
	public ComboExperience modifier(ComboExperience entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return comboExperienceRepository.save(entity);
	}

	@Override
	public List<ComboExperience> findAll() {
		// TODO Auto-generated method stub
		return comboExperienceRepository.findAll();
	}

	@Override
	public ComboExperience findById(Long id) {
		// TODO Auto-generated method stub
		return comboExperienceRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		comboExperienceRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<ComboExperience> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
