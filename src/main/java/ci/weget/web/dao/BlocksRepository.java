package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.Blocks;

public interface BlocksRepository extends JpaRepository<Blocks, Long>{

	// retrouver un block a partir de son libelle
	Blocks findByLibelle(String libelle);
	
	// rechercher un bloc a partir de son libelle
	@Query("select b from Blocks b where b.libelle like %:x%")
	List<Blocks> chercherBlockParMc(@Param("x") String mc);
	
	// ramener un block a partir de son identifiant
	@Query("select b from Blocks b where b.id=?1")
	Blocks getByid(Long id);
	
	/*// retrouver un paiement a partir de lídentifiant dún block
	@Query("select p from Paiement p where p.block.id=?1")
	Blocks getPaiementParIdBlock(Long id);*/
	
}
