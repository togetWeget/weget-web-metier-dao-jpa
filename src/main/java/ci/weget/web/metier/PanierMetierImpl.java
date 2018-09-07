package ci.weget.web.metier;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.BlocksRepository;
import ci.weget.web.dao.PanierRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Block;

import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PanierMetierImpl implements IPanierMetier {

	private Map<Long, Panier> items = new HashMap<>();
	@Autowired
	private TarifRepository tarifRepository;
	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private BlocksRepository blocksRepository;

	@Override
	public boolean ajoutLignePanier(Tarif tarif, Block block, Personne personne, Double quantite, Double total) {
		Tarif t = tarifRepository.getTarifParId(tarif.getId());
		Block b = blocksRepository.getByid(t.getBlock().getId());
		Personne pers = personnesRepository.getPersonneByid(personne.getId());
		Panier p = new Panier();

		p.setBlock(b);
		p.setTarif(tarif);
		p.setPersonne(pers);
		p.setQuantite(quantite);
		p.setTotal(total);

		System.out.println("le panier" + p);
		panierRepository.save(p);
		return true;
	}

	@Override
	public Panier creer(Panier panier) throws InvalideTogetException {
		return panierRepository.save(panier);

	}

	@Override
	public boolean modifLignePanier(Long id,Long version,Tarif tarif, Block block, Personne personne, Double quantite, Double total) {
		Tarif t = tarifRepository.getTarifParId(tarif.getId());
		Block b = blocksRepository.getByid(t.getBlock().getId());
		Personne pers = personnesRepository.getPersonneByid(personne.getId());
		 Panier p = panierRepository.findPanierById(id);

		p.setBlock(b);
		p.setTarif(tarif);
		p.setPersonne(pers);
		p.setQuantite(quantite);
		p.setTotal(total);

		System.out.println("le panier" + p);
		panierRepository.save(p);
		return true;
	}

	@Override
	public List<Panier> findAll() {

		return panierRepository.findAllPanier();
	}

	@Override
	public Panier findById(Long id) {

		return panierRepository.findPanierById(id);
	}

	@Override
	public boolean supprimer(Long id) {

		panierRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Panier> entites) {

		panierRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Collection<Panier> getItems() {
		return items.values();

	}

	public Double getTotal() {
		Double total = 0d;
		for (Panier lc : items.values()) {
			total += lc.getTarif().getPrix() * lc.getQuantite();

		}
		return total;

	}

	public int getSize() {
		return items.size();
	}

	@Override
	public List<Panier> LesPanierDeLaPersonne(long idPersonne) {

		return panierRepository.findAllPanierParPersonneId(idPersonne);
	}

	@Override
	public Panier modifier(Panier entity) throws InvalideTogetException {
		
		return null;
	}

}
