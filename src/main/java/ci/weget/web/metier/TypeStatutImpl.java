package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TypeStatutRepository;
import ci.weget.web.entites.TypeStatut;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TypeStatutImpl implements ITypeStatut{

	@Autowired
	private TypeStatutRepository typeStatutRepository;
	@Override
	public TypeStatut creer(TypeStatut entity) throws InvalideTogetException {
		
		return typeStatutRepository.save(entity);
	}

	@Override
	public TypeStatut modifier(TypeStatut entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return typeStatutRepository.save(entity);
	}

	@Override
	public List<TypeStatut> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeStatut findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<TypeStatut> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
