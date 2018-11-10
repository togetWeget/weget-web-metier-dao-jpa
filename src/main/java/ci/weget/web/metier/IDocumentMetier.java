package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Document;

public interface IDocumentMetier extends Imetier<Document, Long> {
Document chercherDocumentParLibelle(String titre);
List<Document> findDocumentParIdSouBlock(Long id);
}
