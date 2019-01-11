package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CommandeRepository;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CommandeMetierImpl implements ICommandeMetier {
	@Autowired
	CommandeRepository commandeRepository;
	

	@Override
	public Commande creerCommande(Personne pe, double montant) throws InvalideTogetException {
        Commande commande= new Commande();
        
		
        commande.setPersonne(pe);
        commande.setMontant(montant);
		
		
	   return commandeRepository.save(commande);
	}

	@Override
	public Commande modifier(Commande entity) throws InvalideTogetException {

		return commandeRepository.save(entity);
	}

	@Override
	public List<Commande> findAll() {

		return commandeRepository.findAll();
	}

	@Override
	public Commande findById(Long id) {

		return commandeRepository.getCommandeParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		commandeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Commande> entites) {
		commandeRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Commande creer(Commande entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commande commandeParPersonne(Long id) {
		
		return commandeRepository.getCommandeParPersonne(id);
	}

}
