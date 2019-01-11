package ci.weget.web.metier;

import java.util.List;

import org.springframework.core.io.Resource;

import ci.weget.web.entites.competences.DocCompetence;

public interface IDocCompetenceMetier extends Imetier<DocCompetence, Long> {
 DocCompetence chercherDocParTitre(String titre);
 List<DocCompetence>findAllDocumentsParIdAbonne(Long id);
 Resource loadFileAsResource(String titre);
}
