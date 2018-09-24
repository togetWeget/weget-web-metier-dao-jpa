package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.CategoryBlock;

public interface CategoryBlockRepository extends JpaRepository<CategoryBlock, Long> {
	// ramener un CategoryBlock a partir de son identifiant
	@Query("select c from CategoryBlock c where c.id=?1")
	CategoryBlock getCategoryBlockByid(Long id);

	// retrouver un CategoryBlock a partir de son libelle
	@Query("select c from CategoryBlock c where c.libelle=?1")
	CategoryBlock findByLibelle(String libelle);
}
