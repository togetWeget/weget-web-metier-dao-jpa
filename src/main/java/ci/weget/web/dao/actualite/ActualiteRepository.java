package ci.weget.web.dao.actualite;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.actualite.Actualite;

public interface ActualiteRepository extends JpaRepository<Actualite, Long>{

}
