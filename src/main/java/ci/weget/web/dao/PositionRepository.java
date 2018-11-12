package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	// ramener une position a partir de son identifiant
		@Query("select p from Position p where p.id=?1")
		Position getByid(Long id);
		// ramener les abonnes d'un block par libelle
		@Query("select p from Position p  where p.membre.id=?1")
		List<Position> findAllPositionsParIdMembre(Long id);

}
