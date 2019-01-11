package ci.weget.web.dao.combo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.combo.Pays;

public interface PaysRepository extends JpaRepository<Pays, Long> {
	// ramener le pays a partir de son id
		@Query("select p from Pays  p where p.id=?1")
		Pays findPaysParId(Long id);
}
