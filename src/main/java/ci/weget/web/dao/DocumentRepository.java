package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	// rechercher un document a partir de son identifiant
	@Query("select doc from Document doc where doc.id=?1")
	Document cherchereDocumentParId(Long id);

	// rechercher un document a partir de son libelle
	@Query("select doc from Document doc where doc.titre=?1")
	Document cherchereDocumentParLibelle(String titre);

	// recupere les document a partir du sous block
	@Query("select doc from Document doc where doc.sousBlock.id=?1")
	List<Document> findDocumentParIdSouBlock(Long id);
}
