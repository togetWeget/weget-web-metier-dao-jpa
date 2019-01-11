package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.ContratPeriodeRepository;
import ci.weget.web.entites.combo.ContratPeriode;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ContratPeriodeMetierImpl  implements IContratPeriodeMetier{
@Autowired
private ContratPeriodeRepository contratPeriodeRepository;
	
	@Override
	public ContratPeriode creer(ContratPeriode entity) throws InvalideTogetException {
		
		return contratPeriodeRepository.save(entity);
	}

	@Override
	public ContratPeriode modifier(ContratPeriode entity) throws InvalideTogetException {
		
		return contratPeriodeRepository.save(entity);
	}

	@Override
	public List<ContratPeriode> findAll() {
		
		return contratPeriodeRepository.findAll();
	}

	@Override
	public ContratPeriode findById(Long id) {
		
		return contratPeriodeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		contratPeriodeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<ContratPeriode> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
