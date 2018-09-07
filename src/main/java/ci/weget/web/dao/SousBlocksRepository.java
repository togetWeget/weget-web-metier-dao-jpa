package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.SousBlock;

public interface SousBlocksRepository extends JpaRepository<SousBlock, Long> {

	// retrouver un sous block a partir de son libelle
	@Query("select sb from SousBlock sb where sb.libelle=?1")
	SousBlock findByLibelle(String libelle);

	// rechercher un sous bloc a partir de son libelle
	@Query("select sb from SousBlock sb where sb.libelle like %:x%")
	List<SousBlock> chercherSousBlockParMc(@Param("x") String mc);

	

}
