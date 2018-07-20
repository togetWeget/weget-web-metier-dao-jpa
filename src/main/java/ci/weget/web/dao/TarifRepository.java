package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Tarif;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
	// liste des personne d'un block identifie par son libelle
		@Query("select t from Tarif t where t.block.id=?1")
		List<Tarif> getTarifParBlockId(long id);
}
