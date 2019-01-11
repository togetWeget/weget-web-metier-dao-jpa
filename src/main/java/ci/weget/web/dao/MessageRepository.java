package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Message;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.entites.Personne;

public interface MessageRepository extends JpaRepository<Message, Long> {
	// ramener tous les messages d'une personnes par id
	@Query("select m from Messagerie m left join fetch m.message mes left join fetch m.expediteur exp left join fetch m.personne p where p.id=?1")
	List<Message> findMessagesParPersonneId(Long id);

	// recherche d'un utilisateur via son login
	@Query("select m from Messagerie m left join fetch m.message mes left join fetch m.expediteur exp left join fetch m.personne p where mes.id=?1")
	Messagerie findMessageById(Long id);

	// recherche d'un utilisateur via son login
	@Query("select m from Messagerie m where m.id=?1")
	Messagerie findMessagerieById(Long id);
	

	// recuperer une personne a partir de son identifiant
	@Query("select message from Message message where message.id=?1")
	Message getMessageByid(long id);
}
