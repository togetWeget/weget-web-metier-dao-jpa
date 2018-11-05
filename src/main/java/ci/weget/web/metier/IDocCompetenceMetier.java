package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.DocCompetence;

public interface IDocCompetenceMetier extends Imetier<DocCompetence, Long> {
 DocCompetence chercherDocParTitre(String titre);
 List<DocCompetence>findAllDocumentsParIdPersonne(Long id);
}
