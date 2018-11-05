package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.DocCompetence;

public interface DocComptRepository extends JpaRepository<DocCompetence, Long> {
	

	// ramener un block a partir de son identifiant
	@Query("select docCompt from DocCompetence docCompt where docCompt.id=?1")
	DocCompetence getByid(Long id);
	// rechercher un document competence a partir de son titre
	@Query("select docCompt from DocCompetence docCompt where docCompt.titre=?1")
	DocCompetence cherchereDocumentParTitre(String titre);

	// ramener une liste de document  quand on connait l'id du membre
	   @Query("select docCompt from DocCompetence docCompt left join fetch docCompt.membre m where m.id=?1")
		List<DocCompetence> findAllDocumentsParIdPersonne(Long id);
}
