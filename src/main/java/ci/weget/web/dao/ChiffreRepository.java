package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Chiffre;
import ci.weget.web.entites.Temoignage;

public interface ChiffreRepository extends JpaRepository<Chiffre, Long> {
	// ramener un temoignage a partir de son identifiant
	@Query("select c from Chiffre c where c.id=?1")
	Chiffre getByid(Long id);

	/*// ramener les chiffres a d'un sous block
	@Query("select c from Chiffre c where c.sousBlock.id=?1")
	List<Chiffre> getChiffreByIdSousBlock(Long id);*/
}
