package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TarifMetierImpl implements ITarifMetier {
    @Autowired
    private IBlocksMetier blocksMetier;
	@Autowired
	private TarifRepository tarifRepository;
	
	@Override
	public Tarif creer(Tarif entity) throws InvalideTogetException {
		
		return tarifRepository.save(entity);
	}

	@Override
	public Tarif modifier(Tarif entity) throws InvalideTogetException {
		
		return tarifRepository.save(entity);
	}

	@Override
	public List<Tarif> findAll() {
		
		return tarifRepository.findAll();
	}

	@Override
	public Tarif findById(Long id) {
		
		return tarifRepository.getTarifParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		 tarifRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<Tarif> entites) {
		
		tarifRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		return tarifRepository.existsById(id);
	}

	@Override
	public List<Tarif> getTarifParBlockId(Long id) {
		return tarifRepository.getTarifParBlock(id);
	}

	@Override
	public Tarif ajouterBlock(Tarif t, Block b) {
		Block b1= blocksMetier.findById(b.getId());
		t.setBlock(b1);
		return tarifRepository.save(t);
	}

}
