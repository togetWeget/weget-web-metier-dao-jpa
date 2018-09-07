package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

}
