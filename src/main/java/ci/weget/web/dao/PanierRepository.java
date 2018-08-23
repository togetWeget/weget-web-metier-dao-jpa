package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long>{
	// rq,ener tous les panier
		@Query("select p from Panier p ")
		List<Panier> findAllPanier();
		// panier par identifiant
		@Query("select p from Panier p where p.id=?1")
		Panier findPanierById(Long id);
		// supprimer par id
		@Query("DELETE from Panier p where p.id=?1")
		Panier supprimerPanierById(Long id);
		
}
