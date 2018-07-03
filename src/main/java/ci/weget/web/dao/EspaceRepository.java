package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Espaces;




public interface EspaceRepository extends JpaRepository<Espaces, Long> {
	
	Espaces findEspacesByPrix (double prix);
}
