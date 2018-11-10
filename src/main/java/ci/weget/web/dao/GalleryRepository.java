package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Document;
import ci.weget.web.entites.Gallery;


public interface GalleryRepository extends JpaRepository<Gallery, Long> {
	// rechercher une gallery a partir de son libelle
	@Query("select ga from Gallery ga where ga.libelle=?1")
	Gallery chercherGalleryParLibelle(String libelle);

	// rechercher une gallery a partir de son id
	@Query("select ga from Gallery ga where ga.id=?1")
	Gallery chercherGalleryParId(Long id);

	// recupere les gallerie a partir du sous block
	@Query("select ga from Gallery ga where ga.sousBlock.id=?1")
	List<Gallery> findGallerieParIdSouBlock(Long id);

	// recupere les gallerie a partir du membre
	@Query("select ga from Gallery ga where ga.membre.id=?1")
	List<Gallery> findGallerieParIdMembre(Long id);

}
