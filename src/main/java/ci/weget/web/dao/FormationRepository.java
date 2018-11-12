package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Formation;

public interface FormationRepository extends JpaRepository<Formation, Long> {
	// ramener une formation a partir de son identifiant
	@Query("select f from Formation f where f.id=?1")
	Formation getByid(Long id);

	// ramener une formation a par sous block
	@Query("select f from Formation f where f.sousBlock.id=?1")
	List<Formation> getFormationParSousBlock(Long id);

}
