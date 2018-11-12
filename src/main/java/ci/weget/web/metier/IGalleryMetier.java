package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Gallery;


public interface IGalleryMetier extends Imetier<Gallery, Long>{
public Gallery chercherGalleryParLibelle(String libelle);
List<Gallery> findGalleryParIdSouBlock(Long id);
List<Gallery> findGalleryParIdMembre(Long id);

}
