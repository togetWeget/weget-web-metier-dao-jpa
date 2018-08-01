package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAppRoleMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppRoleController {
	@Autowired
	private IAppRoleMetier roleMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	@PostMapping("/roles")
	public String creer(@RequestBody AppRoles role) throws JsonProcessingException {
		Reponse<AppRoles> reponse;

		try {

			AppRoles r1 = roleMetier.creer(role);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", r1.getNom()));
			reponse = new Reponse<AppRoles>(0, messages, r1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<AppRoles>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/roles")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<AppRoles>> reponse;
		try {
			List<AppRoles> roles = roleMetier.findAll();
			reponse = new Reponse<List<AppRoles>>(0, null, roles);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}
