package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Tarif;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
	// liste des tarif d'un block identifie par son id
		@Query("select t from Tarif t  left join fetch t.block b where b.id=?1")
		List<Tarif> getTarifParBlock(long id);
		// le block du tarif par son libelle
		@Query("select t from Tarif t where t.id=?1")
		Tarif getTarifParId(long id);
		
}
