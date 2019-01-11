package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.NiveauEtudeRepository;
import ci.weget.web.entites.combo.NiveauEtude;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class NiveauEtudeMetierImpl implements INiveauEtudeMetier{
@Autowired
private NiveauEtudeRepository niveauEtudeRepository;
	@Override
	public NiveauEtude creer(NiveauEtude entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return niveauEtudeRepository.save(entity);
	}

	@Override
	public NiveauEtude modifier(NiveauEtude entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return niveauEtudeRepository.save(entity);
	}

	@Override
	public List<NiveauEtude> findAll() {
		// TODO Auto-generated method stub
		return niveauEtudeRepository.findAll();
	}

	@Override
	public NiveauEtude findById(Long id) {
		// TODO Auto-generated method stub
		return niveauEtudeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		niveauEtudeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<NiveauEtude> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
