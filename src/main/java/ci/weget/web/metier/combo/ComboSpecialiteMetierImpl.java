package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.SpecialiteRepository;
import ci.weget.web.entites.combo.Specialite;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboSpecialiteMetierImpl implements IComboSpecialiteMetier {
@Autowired
private SpecialiteRepository specialiteRepository;

	@Override
	public Specialite creer(Specialite entity) throws InvalideTogetException {
		
		return specialiteRepository.save(entity);
	}

	@Override
	public Specialite modifier(Specialite entity) throws InvalideTogetException {
		
		return specialiteRepository.save(entity);
	}

	@Override
	public List<Specialite> findAll() {
		
		return specialiteRepository.findAll();
	}

	@Override
	public Specialite findById(Long id) {
		// TODO Auto-generated method stub
		return specialiteRepository.findSpecialiteParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		specialiteRepository.deleteById(id);
		return false;
	}

	@Override
	public boolean supprimer(List<Specialite> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
