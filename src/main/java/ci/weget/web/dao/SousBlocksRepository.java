package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.SousBlock;

public interface SousBlocksRepository extends JpaRepository<SousBlock, Long> {

	// retrouver un sous block a partir de son libelle
	@Query("select sb from SousBlock sb where sb.id=?1")
	SousBlock findSoublockById(Long id);

	// rechercher un sous bloc a partir de son libelle
	@Query("select sb from SousBlock sb where sb.nom like %:x%")
	List<SousBlock> chercherSousBlockParMc(@Param("x") String mc);

	// ramener les sous blocks a partir de id de detail block
	@Query("select sb from SousBlock sb  where sb.detailBlock.id=?1")
	SousBlock findSousBlockParIdDetailBlock(Long id);

	// rechercher les sous blocks par block
	@Query("select sb from SousBlock sb  where sb.idBlock=?1")
	List<SousBlock> findSousBlockParIdBlock(Long id);

	// rechercher les sous blocks par libelle
	@Query("select sb from SousBlock sb  where sb.nom=?1")
	SousBlock findSousBlockParNom(String nom);

}
