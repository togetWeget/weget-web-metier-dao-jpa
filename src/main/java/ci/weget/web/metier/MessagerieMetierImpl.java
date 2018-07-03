package ci.weget.web.metier;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.weget.web.dao.MessageRepository;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class MessagerieMetierImpl  implements ImessagerieMetier{
 private MessageRepository messageRepository;
	@Override
	public Messagerie creer(Messagerie entity) throws InvalideTogetException {
		
		return messageRepository.save(entity);
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
