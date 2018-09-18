package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Gallery;
import ci.weget.web.entites.PhotoGallery;

public interface IGalleryMetier extends Imetier<Gallery, Long>{
public Gallery chercherGalleryParLibelle(String libelle);
/*public List<PhotoGallery>pathPhotoParGalleryId(Long id);*/
}
