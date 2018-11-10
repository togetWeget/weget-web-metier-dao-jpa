package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.DocumentRepository;
import ci.weget.web.entites.Document;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DocumentMetierImpl implements IDocumentMetier {
	@Autowired
	DocumentRepository documentRepository;

	@Override
	public Document creer(Document entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return documentRepository.save(entity);
	}

	@Override
	public Document modifier(Document entity) throws InvalideTogetException {
		
		return documentRepository.save(entity);
	}

	@Override
	public List<Document> findAll() {
		
		return documentRepository.findAll();
	}

	@Override
	public Document findById(Long id) {
		// TODO Auto-generated method stub
		return documentRepository.cherchereDocumentParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Document> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document chercherDocumentParLibelle(String titre) {
		
		return documentRepository.cherchereDocumentParLibelle(titre);
	}

	@Override
	public List<Document> findDocumentParIdSouBlock(Long id) {
		
		return documentRepository.findDocumentParIdSouBlock(id);
	}

}
