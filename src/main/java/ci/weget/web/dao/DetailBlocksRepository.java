package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlocks;
import ci.weget.web.entites.Personnes;

public interface DetailBlocksRepository extends JpaRepository<DetailBlocks, Long> {
	// ramener les membres d'un block par libelle
	@Query("select db from DetailBlocks db where db.blocks.libelle=?1")
	List<Personnes> findAllPersonnesParBlock(String libelle);
}
