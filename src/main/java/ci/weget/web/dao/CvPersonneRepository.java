package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.CvPersonne;

public interface CvPersonneRepository extends JpaRepository<CvPersonne, Long>{
	// ramener les sous blocks a partir de id de detail block
		@Query("select cv from CvPersonne cv  where cv.abonnement.id=?1")
		CvPersonne findCvPersonneParIdAbonnement(Long id);
}
