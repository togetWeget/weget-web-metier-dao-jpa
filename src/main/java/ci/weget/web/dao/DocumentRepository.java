package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{
	// rechercher un document  a partir de son libelle
		@Query("select doc from Document doc where doc.titre=?1")
		Document cherchereDocumentParLibelle(String titre);
}
