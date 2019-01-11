package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.ChiffreRepository;
import ci.weget.web.entites.Chiffre;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
@Transactional
public class ChiffreMetierImpl implements IChiffreMetier {
	@Autowired
	private ChiffreRepository chiffreRepository;

	@Override
	public Chiffre creer(Chiffre entity) throws InvalideTogetException {
		
		
		return chiffreRepository.save(entity);
	}

	@Override
	public Chiffre modifier(Chiffre entity) throws InvalideTogetException {

		return chiffreRepository.save(entity);
	}

	@Override
	public List<Chiffre> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chiffre findById(Long id) {

		return chiffreRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Chiffre> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {

		return false;
	}

	@Override
	public List<Chiffre> getChifreByIdSousBlock(Long id) {

		return null;
	}

}
