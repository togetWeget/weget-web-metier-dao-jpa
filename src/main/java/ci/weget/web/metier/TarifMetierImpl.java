package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TarifMetierImpl implements ITarifMetier {

	@Autowired
	private TarifRepository tarifRepository;
	@Override
	public Blocks getTarifBlockId(Long id) {
		
		return tarifRepository.getTarifBlockId(id);
	}

	@Override
	public Tarif creer(Tarif entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif modifier(Tarif entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tarif> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarif findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Tarif> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
