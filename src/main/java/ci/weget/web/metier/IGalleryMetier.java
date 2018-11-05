package ci.weget.web.metier;

import ci.weget.web.entites.Gallery;


public interface IGalleryMetier extends Imetier<Gallery, Long>{
public Gallery chercherGalleryParLibelle(String libelle);
/*public List<PhotoGallery>pathPhotoParGalleryId(Long id);*/
}
