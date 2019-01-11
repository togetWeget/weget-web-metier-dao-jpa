package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.TypeEtablissement;

public interface TypeEtablissementRepository extends JpaRepository<TypeEtablissement, Long> {
	// ramener un CategoryBlock a partir de son identifiant
	@Query("select te from TypeEtablissement te where te.id=?1")
	TypeEtablissement getTypeEtablissementByid(Long id);

	// retrouver un CategoryBlock a partir de son libelle
	@Query("select te from TypeEtablissement te where te.libelle=?1")
	TypeEtablissement findByLibelle(String libelle);

	

}
