package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.MessageRepository;
import ci.weget.web.dao.MessagerieRepository;
import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.Message;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class MessagerieMetierImpl implements ImessagerieMetier {
	@Autowired
	private MessagerieRepository messagerieRepository;
	@Autowired
	private PersonnesRepository personnesRepository;
	@Autowired
	private MessageRepository messageRepository;
	private JavaMailSender javaMailSender;

	@Override
	public Messagerie creer(Messagerie entity) throws InvalideTogetException {
		Personne p = personnesRepository.getPersonneByid(entity.getPersonne().getId());

		Messagerie msg = new Messagerie();
		msg.setExpediteur(entity.getExpediteur());
		msg.setMessage(entity.getMessage());
		msg.setPersonne(p);
		return messagerieRepository.save(msg);
	}

	@Override
	public Messagerie modifier(Messagerie entity) throws InvalideTogetException {

		return messagerieRepository.save(entity);
	}
   
	@Autowired
	public MessagerieMetierImpl(JavaMailSender javaMailSender) {
	   this.javaMailSender=javaMailSender;
   }
	@Override
	public boolean sendEmail(Messagerie  m) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(m.getPersonne().getAdresse().getEmail());
		mail.setTo(m.getExpediteur().getEmail());
		mail.setSubject(m.getMessage().getSujet());
		mail.setText(m.getMessage().getContenu());
		javaMailSender.send(mail);
		return true;
	}
	@Override
	public List<Messagerie> findAll() {

		return messagerieRepository.findAll();
	}

	@Override
	public Messagerie findById(Long id) {

		return messagerieRepository.getOne(id);
	}

	@Override
	public boolean supprimer(Long id) {
		messageRepository.deleteById(id);
		return true;
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

	@Override
	public List<Message> findMessagesParPersonneId(Long id) {

		return messageRepository.findMessagesParPersonneId(id);
	}

	@Override
	public Messagerie findMessageById(Long id) {

		return messageRepository.findMessageById(id);
	}

	@Override
	public Messagerie modifierMessage(Messagerie messagerie) throws InvalideTogetException {
		Messagerie mes = messageRepository.findMessagerieById(messagerie.getId());
		Message m = mes.getMessage();
		Message m1= messageRepository.getMessageByid(m.getId());
		m1.setStatut(false);
		messageRepository.save(m1);
        return messagerieRepository.save(mes);
	}

}
