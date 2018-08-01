package ci.weget.web.modeles;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ci.weget.web.dao.TarifRepository;
import ci.weget.web.entites.Block;
import ci.weget.web.entites.LigneCommande;
import ci.weget.web.entites.Tarif;

public class Panier implements Serializable {
	
	@Autowired
	private TarifRepository tarifRepository;

	private static final long serialVersionUID = 1L;
	private Map<Long, LigneCommande> items = new HashMap<>();

	private Block b1;

	public void addArticle(Tarif t, Integer quantite) {
		Tarif t1 = tarifRepository.getTarifParId(t.getId());
		Block b1 = t1.getBlock();
		if (items.get(b1.getId()) == null) {
			LigneCommande lc = new LigneCommande();
			lc.setBlocks(b1);;
			lc.setQuantite(quantite);
			lc.setPrix(t1.getPrix());
		} else {
			LigneCommande lc = items.get(t.getId());
			lc.setQuantite(lc.getQuantite() + quantite);
		}
	}

	public Collection<LigneCommande> getItems() {
		return items.values();

	}

	public Double getTotal() {
		Double total = 0d;
		for (LigneCommande lc : items.values()) {
			total += lc.getPrix() * lc.getQuantite();

		}
		return total;

	}

	public int getSize() {
		return items.size();
	}

	public void deleteItem(Long idTarif) {
		Tarif t1 = tarifRepository.getTarifParId(idTarif);
		b1 = t1.getBlock();
		items.remove(b1);
	}
}
