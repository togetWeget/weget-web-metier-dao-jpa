package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlocks;

public interface DetailBlocksRepository extends JpaRepository<DetailBlocks, Long> {
	// ramener les membres d'un block par libelle
	@Query("select db from DetailBlocks db where db.blocks.libelle=?1")
	List<DetailBlocks> findAllPersonnesParBlock(String libelle);
	@Query("select db from DetailBlocks db where db.personne.id=?1")
	List<DetailBlocks> findAllBlocksParPersonne(Long id);
}
