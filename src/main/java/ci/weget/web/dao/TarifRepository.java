package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Tarif;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
	// obtenir un block a partir de son identifiant
		@Query("select t.block from Tarif t where t.block.id=?1")
		Blocks getTarifBlockId(Long id);
}
