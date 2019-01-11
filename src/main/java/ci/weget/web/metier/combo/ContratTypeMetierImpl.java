package ci.weget.web.metier.combo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.combo.ContratTypeRepository;
import ci.weget.web.entites.combo.ContratType;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ContratTypeMetierImpl implements IContratTypeMetier {
@Autowired
private ContratTypeRepository contratTypeRepository;

@Override
public ContratType creer(ContratType entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return contratTypeRepository.save(entity);
}

@Override
public ContratType modifier(ContratType entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return contratTypeRepository.save(entity);
}

@Override
public List<ContratType> findAll() {
	// TODO Auto-generated method stub
	return contratTypeRepository.findAll();
}

@Override
public ContratType findById(Long id) {
	// TODO Auto-generated method stub
	return contratTypeRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	contratTypeRepository.deleteById(id);
	return true;
}

@Override
public boolean supprimer(List<ContratType> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}
}
