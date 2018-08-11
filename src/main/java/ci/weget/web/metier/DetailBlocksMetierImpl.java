package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Membre;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;

@Service
public class DetailBlocksMetierImpl implements IDetailBlocksMetier {

	@Autowired
	private DetailBlocksRepository detailBlocksRepository;
	@Autowired
	private BlocksRepository blocksRepository;
	@Autowired
	private PersonnesRepository personnesRepository;

	
	@Override
	public DetailBlock ajoutdetailBlocks(Personne personne,Block block) {
		
		return detailBlocksRepository.save(new DetailBlock(personne, block));
	}

	@Override
	public List<DetailBlock> findAll() {

		return null;
	}

	@Override
	public DetailBlock findById(Long id) {

		return null;
	}

	@Override
	public boolean supprimer(Long id) {

		return false;
	}

	@Override
	public boolean supprimer(List<DetailBlock> entites) {

		return false;
	}

	@Override
	public boolean existe(Long id) {

		return false;
	}

	@Override
	public DetailBlock creer(DetailBlock entity) throws InvalideTogetException {

		return null;
	}

	@Override
	public DetailBlock modifier(DetailBlock entity) throws InvalideTogetException {

		return null;
	}

	@Override
	public void addMembresToBlocks(String statut, String blockName) {

	}
	
}
