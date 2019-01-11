package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.PaiementRepository;
import ci.weget.web.entites.Paiement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PaiementMetierImpl implements IPaiementMetier {

	@Autowired
	PaiementRepository paiementRepository;
	@Override
	public Paiement creer(Paiement entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return paiementRepository.save(entity);
	}

	@Override
	public Paiement modifier(Paiement entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return paiementRepository.save(entity);
	}

	@Override
	public List<Paiement> findAll() {
		// TODO Auto-generated method stub
		return paiementRepository.findAll();
	}

	@Override
	public Paiement findById(Long id) {
		
		return paiementRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Paiement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public Paiement getPaiementDeCommande(Long id) {
		// TODO Auto-generated method stub
		return paiementRepository.getPaiementDeCommande(id);
	}
*/
}
