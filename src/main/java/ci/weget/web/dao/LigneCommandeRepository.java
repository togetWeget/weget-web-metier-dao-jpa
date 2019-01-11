package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

}
