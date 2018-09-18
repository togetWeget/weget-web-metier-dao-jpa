package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;

public interface DetailBlocksRepository extends JpaRepository<DetailBlock, Long> {

	// ramener tous les detail block
	@Query("select db from DetailBlock db")
	List<DetailBlock> findAlldetailBlock();

	// ramener les abonnes d'un block par libelle
	@Query("select db from DetailBlock db  where db.block.libelle=?1")
	List<DetailBlock> findAllPersonnesParBlock(String libelle);

	// ramener une liste de detail block quand on connait l'id du block
	@Query("select db from DetailBlock db  where db.block.id=?1")
	List<DetailBlock> findAllBlocksParPersonne(Long id);

	// ramener le detail block d'un block a partir de son id
	@Query("select db from DetailBlock db where db.id=?1")
	DetailBlock findDtailBlocksParId(Long id);

	// ramener les details blocks d'une personne a partir de son identifianr
	@Query("select db from DetailBlock db left join fetch db.block b left join fetch db.membre p where p.id=?1")
	List<DetailBlock> findDtailBlocksParPersonneId(Long id);

	// ramener tous les blocks d'une personnes par login
	@Query("select db from DetailBlock db left join fetch db.block b left join fetch db.membre p where p.login=?1")
	List<DetailBlock> findDtailBlocksParPersonneLogin(String login);

	// ramener une personne a partir de detail block
	@Query("select db from DetailBlock db left join fetch db.block b left join fetch db.membre p where p.id=?1")
	DetailBlock findPersonneParId(Long id);
	

	// ramener le detail block  a partir de block et de personne
	@Query("select db from DetailBlock db left join fetch db.block b left join fetch db.membre p where p.id=?1 AND  b.id=?2")
	DetailBlock findDetailBlockIdPerAndIdBlock(Long idPersonne, Long idBlock);

	// ramener les abonnes d'un block par id
	@Query("select db from DetailBlock db  where db.block.id=?1")
	List<DetailBlock> personneALLBlock(Long id);
}
