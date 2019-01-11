package ci.weget.web.dao.combo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.combo.Quartier;
import ci.weget.web.entites.combo.Ville;

public interface QuartierRepository extends JpaRepository<Quartier, Long> {

	// ramener le quartier a partir de son id
	@Query("select q from Quartier  q where q.id=?1")
	Quartier findQuartierParId(Long id);

	// ramener les quartier par ville
	@Query("select q from Quartier q  where q.ville.id=?1")
	List<Quartier> findAllQuartierParville(Long id);

}
