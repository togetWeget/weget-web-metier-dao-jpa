package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long>{
	//  recupere paiement a partir du libelle du block
		@Query("select p from Paiement p where p.blocks.libelle=?1")
		Paiement getPaiementParBlockLibelle(String libelle);
}
