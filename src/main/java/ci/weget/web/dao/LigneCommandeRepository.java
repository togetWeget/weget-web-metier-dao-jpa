package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
	// ramener les panier d'une personne par id
		@Query("select lg from LigneCommande lg  where lg.personne.id=?1")
		List<LigneCommande> findAllLigneCommandeParPersonneId(Long id);
}
