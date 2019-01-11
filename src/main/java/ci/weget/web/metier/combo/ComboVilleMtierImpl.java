package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.VilleRepository;
import ci.weget.web.entites.combo.Ville;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ComboVilleMtierImpl implements IComboVilleMetier {

	@Autowired
	VilleRepository villeRepository;
	@Override
	public Ville creer(Ville entity) throws InvalideTogetException {
		
		return villeRepository.save(entity);
	}

	@Override
	public Ville modifier(Ville entity) throws InvalideTogetException {
		
		return villeRepository.save(entity);
	}

	@Override
	public List<Ville> findAll() {
		
		return villeRepository.findAll();
	}

	@Override
	public Ville findById(Long id) {
	
		return villeRepository.findVilleParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		villeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Ville> entites) {
		villeRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		villeRepository.existsById(id);
		return true;
	}

	@Override
	public List<Ville> findAllVilleParPays(Long id) {
	
		return villeRepository.findAllVilleParPays(id);
	}

}
