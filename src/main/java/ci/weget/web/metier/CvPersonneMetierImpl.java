package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CvPersonneRepository;
import ci.weget.web.entites.CvPersonne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CvPersonneMetierImpl implements ICvPersonneMetier {
@Autowired
CvPersonneRepository cvPersonneRepository;
	@Override
	public CvPersonne creer(CvPersonne entity) throws InvalideTogetException {
		
		return cvPersonneRepository.save(entity);
	}

	@Override
	public CvPersonne modifier(CvPersonne entity) throws InvalideTogetException {
		
		return cvPersonneRepository.save(entity);
	}

	@Override
	public List<CvPersonne> findAll() {
		
		return cvPersonneRepository.findAll();
	}

	@Override
	public CvPersonne findById(Long id) {
		
		return cvPersonneRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<CvPersonne> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return cvPersonneRepository.existsById(id);
	}

	@Override
	public CvPersonne findCvPersonneParIdAbonnement(Long id) {
		// TODO Auto-generated method stub
		return cvPersonneRepository.findCvPersonneParIdAbonnement(id);
	}

}
