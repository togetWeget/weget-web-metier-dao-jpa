package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.ComboTarifRepository;
import ci.weget.web.entites.combo.ComboTarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboTarifMetierImpl implements IComboTarifMetier {
@Autowired
private ComboTarifRepository comboTarifRepository;
	@Override
	public ComboTarif creer(ComboTarif entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return comboTarifRepository.save(entity);
	}

	@Override
	public ComboTarif modifier(ComboTarif entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return comboTarifRepository.save(entity);
	}

	@Override
	public List<ComboTarif> findAll() {
		// TODO Auto-generated method stub
		return comboTarifRepository.findAll();
	}

	@Override
	public ComboTarif findById(Long id) {
		// TODO Auto-generated method stub
		return comboTarifRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		comboTarifRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<ComboTarif> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
