package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
	// ramener un block a partir de son identifiant
		@Query("select p from Paiement p where p.id=?1")
		Paiement getByid(Long id);
		/*//obtenir la commade d'un paiement
		@Query("select p from Paiement p where p.commande.id=?1")
		Paiement getPaiementDeCommande(Long id);*/
}
