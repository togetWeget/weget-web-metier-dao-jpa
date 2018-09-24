package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TemoignageRepository;
import ci.weget.web.entites.Temoignage;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TemoignageMetierImpl implements ITemoignageMetier {
 @Autowired
 private TemoignageRepository temoignageRepository;
	@Override
	public Temoignage creer(Temoignage entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return temoignageRepository.save(entity);
	}

	@Override
	public Temoignage modifier(Temoignage entity) throws InvalideTogetException {
		
		return temoignageRepository.save(entity);
	}

	@Override
	public List<Temoignage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temoignage findById(Long id) {
		
		return temoignageRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Temoignage> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Temoignage> getTemoignageByIdSousBlock(Long id) {
		
		return null;
	}

}
