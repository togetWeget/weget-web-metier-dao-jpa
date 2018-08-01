package ci.weget.web.metier;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.weget.web.entites.LigneCommande;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class LigneCommandeMtierImpl implements ILigneCommandeMetier{

	@Override
	public LigneCommande creer(LigneCommande entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCommande modifier(LigneCommande entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LigneCommande> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCommande findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<LigneCommande> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
