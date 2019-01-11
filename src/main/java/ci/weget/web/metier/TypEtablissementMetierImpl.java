package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TypeEtablissementRepository;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.TypeEtablissement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TypEtablissementMetierImpl implements ITypeEtablissementMetier {
@Autowired
private TypeEtablissementRepository typeEtablissement;
	@Override
	public TypeEtablissement creer(TypeEtablissement entity) throws InvalideTogetException {
		
		return typeEtablissement.save(entity);
	}

	@Override
	public TypeEtablissement modifier(TypeEtablissement entity) throws InvalideTogetException {
		
		return typeEtablissement.save(entity);
	}

	@Override
	public List<TypeEtablissement> findAll() {
		
		return typeEtablissement.findAll();
	}

	@Override
	public TypeEtablissement findById(Long id) {
		
		return typeEtablissement.getTypeEtablissementByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<TypeEtablissement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeEtablissement rechercheParLibelle(String libelle) {
		
		return typeEtablissement.findByLibelle(libelle);
	}

	

}
