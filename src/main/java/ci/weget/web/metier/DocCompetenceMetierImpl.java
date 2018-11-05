package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.DocComptRepository;
import ci.weget.web.entites.DocCompetence;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class DocCompetenceMetierImpl implements IDocCompetenceMetier {
@Autowired
private DocComptRepository docCompetenceRepository;

@Override
public DocCompetence creer(DocCompetence entity) throws InvalideTogetException {
	
	return docCompetenceRepository.save(entity);
}

@Override
public DocCompetence modifier(DocCompetence entity) throws InvalideTogetException {
	// TODO Auto-generated method stub
	return docCompetenceRepository.save(entity);
}

@Override
public List<DocCompetence> findAll() {
	
	return docCompetenceRepository.findAll();
}

@Override
public DocCompetence findById(Long id) {
	
	return docCompetenceRepository.getByid(id);
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean supprimer(List<DocCompetence> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public DocCompetence chercherDocParTitre(String titre) {
	
	return docCompetenceRepository.cherchereDocumentParTitre(titre);
}

@Override
public List<DocCompetence> findAllDocumentsParIdPersonne(Long id) {
	
	return docCompetenceRepository.findAllDocumentsParIdPersonne(id);
}
	

   
}
