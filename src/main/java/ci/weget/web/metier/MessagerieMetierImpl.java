package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.MessageRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class MessagerieMetierImpl  implements ImessagerieMetier{
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private PersonnesRepository personnesRepository;

 @Override
	public Messagerie creer(Messagerie entity) throws InvalideTogetException {
		Personne p = personnesRepository.getPersonneByid(entity.getPersonne().getId());
		
		Messagerie msg = new Messagerie();
		msg.setExpediteur(entity.getExpediteur());
		msg.setMessages(entity.getMessages());
		msg.setPersonne(p);
		return messageRepository.save(msg);
	}
	@Override
	public Messagerie modifier(Messagerie entity) throws InvalideTogetException {
		
		return messageRepository.save(entity);
	}

	@Override
	public List<Messagerie> findAll() {
		
		return messageRepository.findAll();
	}

	@Override
	public Messagerie findById(Long id) {
		
		return messageRepository.getOne(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Messagerie> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
