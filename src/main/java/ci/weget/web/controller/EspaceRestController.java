package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Espaces;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;



@RestController
public class EspaceRestController {
 
	@Autowired
	private IEspaceMetier espaceMetier;

	@Autowired
	private ObjectMapper jsonMapper;
	
	@PostMapping("/espaces")
	public String creer(@RequestBody Espaces entity) throws JsonProcessingException {
		Reponse<Espaces> reponse= null;

		try {

			Espaces e1 = espaceMetier.creer(entity);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s Votre espace à été créer avec succes", e1.getPrix(), e1.getDescription()));
			reponse = new Reponse<Espaces>(0, messages, e1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Espaces>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	public Boolean ajoutEspaceToPersonne(Personnes personnes, double prix) {
		return espaceMetier.ajoutEspaceToPersonne(personnes, prix);
	}

	public Espaces modifier(Espaces entity) throws InvalideTogetException {
		return espaceMetier.modifier(entity);
	}
    @GetMapping("/espaces")
	public List<Espaces> findAllEspaces() {
		return espaceMetier.findAll();
	}

	public Espaces findById(Long id) {
		return espaceMetier.findById(id);
	}

	public boolean supprimer(Long id) {
		return espaceMetier.supprimer(id);
	}

	public boolean supprimer(List<Espaces> entites) {
		return espaceMetier.supprimer(entites);
	}

	public boolean existe(Long id) {
		return espaceMetier.existe(id);
	}

}
