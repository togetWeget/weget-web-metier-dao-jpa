package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.GalleryRepository;
import ci.weget.web.entites.Gallery;
import ci.weget.web.entites.PhotoGallery;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class GalleryMetierImpl implements IGalleryMetier{
	@Autowired
	private GalleryRepository galleryRepository;
	@Override
	public Gallery creer(Gallery entity) throws InvalideTogetException {
		
		return galleryRepository.save(entity);
	}

	@Override
	public Gallery modifier(Gallery entity) throws InvalideTogetException {
		
		return galleryRepository.save(entity);
	}

	@Override
	public List<Gallery> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gallery findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Gallery> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Gallery chercherGalleryParLibelle(String libelle) {
		
		return galleryRepository.chercherGalleryParLibelle(libelle) ;
	}

/*	@Override
	public List<PhotoGallery> pathPhotoParGalleryId(Long id) {
		
		return galleryRepository.pathPhotoParGalleryId(id);
	}*/

}
