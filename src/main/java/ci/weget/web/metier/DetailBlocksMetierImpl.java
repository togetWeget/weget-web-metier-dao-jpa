package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.DetailBlocksRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Membres;
import ci.weget.web.entites.Personnes;
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
	public Membres saveMembres(Membres membre) {

		return null;
	}

	@Override
	public DetailBlocks saveDetailBlocks(DetailBlocks db) {

		return null;
	}

	@Override
	public List<DetailBlocks> findAll() {

		return null;
	}

	@Override
	public DetailBlocks findById(Long id) {

		return null;
	}

	@Override
	public boolean supprimer(Long id) {

		return false;
	}

	@Override
	public boolean supprimer(List<DetailBlocks> entites) {

		return false;
	}

	@Override
	public boolean existe(Long id) {

		return false;
	}

	@Override
	public DetailBlocks creer(DetailBlocks entity) throws InvalideTogetException {

		return null;
	}

	@Override
	public DetailBlocks modifier(DetailBlocks entity) throws InvalideTogetException {

		return null;
	}

	@Override
	public void addMembresToBlocks(String statut, String blockName) {

	}

}
