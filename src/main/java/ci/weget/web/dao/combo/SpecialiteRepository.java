package ci.weget.web.dao.combo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.combo.Specialite;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long>{
	// ramener le pays a partir de son id
			@Query("select sp from Specialite  sp where sp.id=?1")
			Specialite findSpecialiteParId(Long id);
}
