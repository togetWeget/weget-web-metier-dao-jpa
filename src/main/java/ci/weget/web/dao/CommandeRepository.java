package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
	// recupere paiement a partir de son id
	@Query("select c from Commande c where c.id=?1")
	Commande getCommandeParId(Long id);

	// recupere paiement a partir de son id
	@Query("select c from Commande c where c.personne.id=?1")
	Commande getCommandeParPersonne(Long id);
}
