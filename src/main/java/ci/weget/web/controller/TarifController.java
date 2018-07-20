package ci.weget.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Tarif;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ITarifMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
public class TarifController {
	
	@Autowired
	private ITarifMetier tarifMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	@GetMapping("/tarifsBlocksId/{id}")
	public String obtenirTariParIdBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Tarif>> reponse;
		try {
			List<Tarif> tarifs = tarifMetier.getTarifParBlockId(id);
			reponse = new Reponse<List<Tarif>>(0, null, tarifs);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
