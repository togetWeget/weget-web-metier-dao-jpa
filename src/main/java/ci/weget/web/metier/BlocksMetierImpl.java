package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Personnes;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class BlocksMetierImpl implements IBlocksMetier {

	@Autowired
	private BlocksRepository blocksRepository;

	@Override
	public Blocks creer(Blocks block) throws InvalideTogetException {
		if ((block.getLibelle() == null) || (block.getLibelle() == "")) {
			throw new InvalideTogetException("Le libelle ne peut etre null");
		}

		Blocks blocks = null;
		try {
			blocks = blocksRepository.findByLibelle(block.getLibelle());
		} catch (Exception e) {
			throw new InvalideTogetException("probleme de connexion");
		}
		if (blocks != null)
			throw new InvalideTogetException("ce libelle  existe deja");

		return blocksRepository.save(block);

	}

	@Override
	public Blocks modifier(Blocks block) throws InvalideTogetException {
		Blocks b = blocksRepository.findByLibelle(block.getLibelle());

		if (b != null && b.getId() != block.getId()) {

			throw new InvalideTogetException("ce block est deja utilise");

		}

		return blocksRepository.save(block);
	}
	@Override
	public List<Blocks> chercherBlockParMc(String mc) {
		
		return blocksRepository.chercherBlockParMc(mc);
	}

	@Override
	public List<Personnes> getPersonnes(Long id) {
		
		return blocksRepository.getPersonnes(id);
	}
	@Override
	public List<Personnes> getPersonnesParBlockLibelle(String libelle) {
		
		return blocksRepository.getPersonnesParBlockLibelle(libelle);
	}


	
	@Override
	public List<Blocks> findAll() {
		return blocksRepository.findAll();
	}

	@Override
	public Blocks findById(Long id) {
		return blocksRepository.getByid(id);
	}
	
	@Override
	public boolean supprimer(Long id) {
		blocksRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Blocks> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Blocks rechercheParLibelle(String libelle) {

		return blocksRepository.findByLibelle(libelle);
	}

	
}
