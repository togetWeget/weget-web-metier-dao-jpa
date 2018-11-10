package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.FlashInfo;

public interface FlashInfoRepository extends JpaRepository<FlashInfo, Long> {
	// ramener un flash info a partir de son identifiant
	@Query("select f from FlashInfo f where f.id=?1")
	FlashInfo getByid(Long id);

	// ramener les abonnes d'un block par libelle
	@Query("select f from FlashInfo f  where f.sousBlock.id=?1")
	List<FlashInfo> findAllFlashInfoParIdSousBlock(Long id);
}
