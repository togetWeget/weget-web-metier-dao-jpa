package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long>{
	//  recupere paiement a partir du libelle de Personne
		@Query("select paie from Paiement paie where paie.personne.id=?1")
		Paiement getPaiementParPersonne(Long id);
}
