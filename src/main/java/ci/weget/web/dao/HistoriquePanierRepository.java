package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.HistoriquePanier;

public interface HistoriquePanierRepository extends JpaRepository<HistoriquePanier, Long> {

}
