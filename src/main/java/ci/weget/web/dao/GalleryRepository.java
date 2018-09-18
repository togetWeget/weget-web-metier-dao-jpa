package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Gallery;
import ci.weget.web.entites.PhotoGallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
	// rechercher une gallery a partir de son libelle
	@Query("select ga from Gallery ga where ga.libelle=?1")
	Gallery chercherGalleryParLibelle(String libelle);
	// ramener les pathphoto d'une gallery par id
	/*@Query("select ph from PhotoGallery ph  where ph.gallery.id=?1")
	List<PhotoGallery> pathPhotoParGalleryId(Long id);*/
}
