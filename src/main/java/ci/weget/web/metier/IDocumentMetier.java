package ci.weget.web.metier;

import ci.weget.web.entites.Document;

public interface IDocumentMetier extends Imetier<Document, Long> {
Document chercherDocumentParLibelle(String titre);
}
