package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.SousBlocksRepository;
import ci.weget.web.entites.SousBlock;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class SousBlockMetierImpl implements ISousBlockMetier {
@Autowired
private SousBlocksRepository sousBlocksRepository;
	@Override
	public SousBlock creer(SousBlock entity) throws InvalideTogetException {
		
		return sousBlocksRepository.save(entity);
	}

	@Override
	public SousBlock modifier(SousBlock entity) throws InvalideTogetException {
		
		return sousBlocksRepository.save(entity);
	}

	@Override
	public List<SousBlock> findAll() {
		
		return sousBlocksRepository.findAll();
	}

	@Override
	public SousBlock findById(Long id) {
		
		return sousBlocksRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		
		sousBlocksRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<SousBlock> entites) {
		
		 sousBlocksRepository.deleteAll(entites);
		 return true;
	}

	@Override
	public boolean existe(Long id) {
		
		return sousBlocksRepository.existsById(id);
	}

	@Override
	public SousBlock rechercheSousBlockParId(Long id) {
		
		return sousBlocksRepository.findSoublockById(id);
	}

	@Override
	public List<SousBlock> chercherSousBlockParMc(String mc) {
		
		return sousBlocksRepository.chercherSousBlockParMc(mc);
	}

	@Override
	public List<SousBlock> findSousBlockParIdBlock(Long id) {
		
		return sousBlocksRepository.findSousBlockParIdBlock(id);
	}

}
