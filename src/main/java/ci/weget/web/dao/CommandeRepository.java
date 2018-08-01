package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

}
