package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Message;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;

public interface ImessagerieMetier extends Imetier<Messagerie, Long> {
public List<Message> findMessagesParPersonneId(Long id);
public Messagerie findMessageById(Long id);
public Messagerie modifierMessage(Messagerie messagerie) throws InvalideTogetException;

}
