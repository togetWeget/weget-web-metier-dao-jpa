package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Messagerie;

public interface MessagerieRepository extends JpaRepository<Messagerie, Long> {

}
