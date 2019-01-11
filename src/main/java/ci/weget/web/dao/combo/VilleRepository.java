package ci.weget.web.dao.combo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.combo.Pays;
import ci.weget.web.entites.combo.Ville;

public interface VilleRepository extends JpaRepository<Ville, Long> {

	// ramener la ville a partir de son id
	@Query("select v from Ville  v where v.id=?1")
	Ville findVilleParId(Long id);

	// ramener les ville par pays
	@Query("select v from Ville v  where v.pays.id=?1")
	List<Ville> findAllVilleParPays(Long id);

}
