package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.EspaceRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Espaces;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;



@Service
public class IEspaceMetierImpl implements IEspaceMetier {

	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private EspaceRepository espaceRepository;

	@Override
	public Espaces creer(Espaces entity) throws InvalideTogetException {
		
		return espaceRepository.save(entity);
	}

	@Override
	public Espaces modifier(Espaces entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return espaceRepository.save(entity);
	}

	@Override
	public List<Espaces> findAll() {
		
		return espaceRepository.findAll();
	}

	@Override
	public Espaces findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Espaces> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean ajoutEspaceToPersonne(Personnes personnes, double prix) {
	Personnes pers = personnesRepository.getOne(personnes.getId());
	Espaces esp = espaceRepository.findEspacesByPrix(prix);
	esp.setPersonnes(pers);
	espaceRepository.save(esp);
	return true;
	}

}
