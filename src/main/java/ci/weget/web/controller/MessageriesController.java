package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Message;
import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ImessagerieMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class MessageriesController {
	@Autowired
	private ImessagerieMetier messagerieMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	@PostMapping("/messageries")
	public String creer(@RequestBody Messagerie msg) throws JsonProcessingException {
		Reponse<Messagerie> reponse;

		try {

			Messagerie m1 = messagerieMetier.creer(msg);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", m1.getId()));
			reponse = new Reponse<Messagerie>(0, messages, m1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Messagerie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("messageries/")
	public Messagerie modifier(@RequestBody Messagerie entity) throws InvalideTogetException {
		return messagerieMetier.modifier(entity);
	}
	@PutMapping("messages/")
	public String modifierStatutMessage(@RequestBody Messagerie entity) throws InvalideTogetException, JsonProcessingException {
		Reponse<Messagerie> reponse;

		try {

			Messagerie m1 = messagerieMetier.modifierMessage(entity);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été modifie avec succes", m1.getId()));
			reponse = new Reponse<Messagerie>(0, messages, m1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Messagerie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/messageries/{id}")
	public String findAllMessagerisParPersonne(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Message>> reponse;
		try {
			List<Message> messages = messagerieMetier.findMessagesParPersonneId(id);
			reponse = new Reponse<List<Message>>(0, null, messages);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/message/{id}")
	public String findMessageParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<Messagerie> reponse;
		try {
			Messagerie messagerie = messagerieMetier.findMessageById(id);
			reponse = new Reponse<Messagerie>(0, null, messagerie);
		} catch (Exception e) {
			reponse = new Reponse<Messagerie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	public Messagerie findById(Long id) {
		return messagerieMetier.findById(id);
	}

	public boolean supprimer(Long id) {
		return messagerieMetier.supprimer(id);
	}

	public boolean supprimer(List<Messagerie> entites) {
		return messagerieMetier.supprimer(entites);
	}

	public boolean existe(Long id) {
		return messagerieMetier.existe(id);
	}

}
