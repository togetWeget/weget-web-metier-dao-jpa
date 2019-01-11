package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.DisponibiliteRepository;
import ci.weget.web.entites.combo.Disponibilite;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DisponibiliteMetierImpl implements IDisponibiliteMetier{
@Autowired
private DisponibiliteRepository disponibiliteRepository;

@Override
public Disponibilite creer(Disponibilite entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return disponibiliteRepository.save(entity);
}

@Override
public Disponibilite modifier(Disponibilite entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return disponibiliteRepository.save(entity);
}

@Override
public List<Disponibilite> findAll() {
	// TODO Auto-generated method stub
	return disponibiliteRepository.findAll();
}

@Override
public Disponibilite findById(Long id) {
	// TODO Auto-generated method stub
	return disponibiliteRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	disponibiliteRepository.deleteById(id);
	return true;
}

@Override
public boolean supprimer(List<Disponibilite> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}
}
