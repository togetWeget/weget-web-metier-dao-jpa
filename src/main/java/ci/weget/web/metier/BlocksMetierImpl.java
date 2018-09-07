package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class BlocksMetierImpl implements IBlocksMetier {
	@Autowired
	private DetailBlocksRepository detailBlocksRepository;
	@Autowired
	private BlocksRepository blocksRepository;

	@Override
	public Block creer(Block block) throws InvalideTogetException {
		if ((block.getLibelle() == null) || (block.getLibelle() == "")) {
			throw new InvalideTogetException("Le libelle ne peut etre null");
		}

		Block blocks = null;

		blocks = blocksRepository.findByLibelle(block.getLibelle());
		if (blocks != null)
			throw new InvalideTogetException("ce libelle existe deja");

		return blocksRepository.save(block);
	}

	@Override
	public Block modifier(Block block) throws InvalideTogetException {
		
		/*Block blocks = blocksRepository.findByLibelle(block.getLibelle());
		if (blocks.getVersion() != block.getVersion())
			throw new InvalideTogetException("ce libelle a deja ete modifier");*/
		
		return blocksRepository.save(block);
	}

	@Override
	public List<Block> chercherBlockParMc(String mc) {

		return blocksRepository.chercherBlockParMc(mc);
	}

	@Override
	public List<Personne> getPersonnes(Long id) {

		return blocksRepository.getPersonnes(id);
	}

	@Override
	public List<Block> findAll() {
		return blocksRepository.findAll();
	}

	@Override
	public Block findById(Long id) {
		return blocksRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		blocksRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Block> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block rechercheParLibelle(String libelle) {

		return blocksRepository.findByLibelle(libelle);
	}

}
