package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Messagerie;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ImessagerieMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
	@PutMapping("messageries")
	public Messagerie modifier(Messagerie entity) throws InvalideTogetException {
		return messagerieMetier.modifier(entity);
	}
   @GetMapping("Messageries")
	public List<Messagerie> findAll() {
		return messagerieMetier.findAll();
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
