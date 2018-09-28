package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	// ramener une position a partir de son identifiant
		@Query("select p from Position p where p.id=?1")
		Position getByid(Long id);
}
