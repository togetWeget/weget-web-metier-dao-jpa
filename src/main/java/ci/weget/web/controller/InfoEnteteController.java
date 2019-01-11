package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.InfoEntete;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IinfoEnteteMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InfoEnteteController  {
	@Autowired
	private IinfoEnteteMetier infoEnteteMetier;

	@Autowired
	private ObjectMapper jsonMapper;
	
	
	@PostMapping("/infoEntete")
	public String creer(@RequestBody InfoEntete entite) throws JsonProcessingException {
		Reponse<InfoEntete> reponse;
		try {
			InfoEntete info = infoEnteteMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", info.getLibelle()));
			reponse = new Reponse<InfoEntete>(0, messages, info);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<InfoEntete>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	
}
