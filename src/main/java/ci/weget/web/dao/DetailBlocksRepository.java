package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

public interface DetailBlocksRepository extends JpaRepository<DetailBlock, Long> {
	// ramener les membres d'un block par libelle
	@Query("select db from DetailBlock db  where db.block.libelle=?1")
	List<DetailBlock> findAllPersonnesParBlock(String libelle);

	@Query("select db from DetailBlock db where db.block.id=?1")
	List<DetailBlock> findAllAbonneParBloc(Long id);

	@Query("select db from DetailBlock db  where db.block.id=?1")
	List<DetailBlock> findAllBlocksParPersonne(Long id);

	@Query("select db from DetailBlock db left join fetch db.block b left join fetch db.personne p where db.id=?1")
	DetailBlock findDtailBlocksParId(Long id);

}
