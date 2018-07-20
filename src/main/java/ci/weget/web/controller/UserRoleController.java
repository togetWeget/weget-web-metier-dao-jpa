package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Personnes;
import ci.weget.web.metier.IAppRoleMetier;
import ci.weget.web.metier.IPersonneMetier;
import ci.weget.web.metier.IUserRoleMetier;
import ci.weget.web.modeles.PostAjoutUserRole;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserRoleController {
	@Autowired
	private IUserRoleMetier userRoleMetier;
	@Autowired
	private IAppRoleMetier roleMetier;
	@Autowired
	private IPersonneMetier personneMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	// recuprer le role a partir de son identifiant
	private Reponse<AppRoles> getRole(long id) {
		// on récupère le block
		AppRoles role = null;
		try {
			role = roleMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1),null);
		}
		// block existant ?
		if (role == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse(2, messages,null);
		}
		// ok
		return new Reponse(0,null, role);
	}
	// recuperer la personne a partir de son identifiant
	private Reponse<Personnes> getPersonneById(final Long id) {
		Personnes personne = null;
		try {
			personne = personneMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personnes>(2, messages, null);
		}
		return new Reponse<Personnes>(0, null, personne);

	}
	// ajouter un detail block 
	@RequestMapping(value = "/ajouterUR")
	public String ajouterUR(@RequestBody PostAjoutUserRole post) throws JsonProcessingException {
		// on récupère les valeurs postées
		Reponse<UserRoles> reponse = null;
		long idRole = post.getIdRole();
		long idPersonne = post.getIdPersonne();
		
		// on récupère le block
		Reponse<AppRoles> reponseRole = getRole(idRole);
		if (reponseRole.getStatut() != 0) {
			return reponseRole.getBody().getNom();
		}
		AppRoles role  = (AppRoles) reponseRole.getBody();
		// on récupère la personne
		
		Reponse<Personnes> reponsePersonne = getPersonneById(idPersonne);
		if (reponsePersonne.getStatut()!= 0) {
			//reponse.incrStatusBy(2);
			return reponsePersonne.getBody().getNomComplet();
		}
		Personnes personne = (Personnes) reponsePersonne.getBody();
		// on ajoute le Rv
		UserRoles ur = null;
		try {
			ur = userRoleMetier.ajoutUserRole(personne, role);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s  à été créer avec succes", ur.getRoles().getNom(), ur.getPersonnes().getNomComplet()));
			reponse = new Reponse<UserRoles>(0, messages, ur);
		} catch (Exception e1) {
			
        reponse = new Reponse<UserRoles>(1, Static.getErreursForException(e1), null);

		}
		// on rend la réponse
		
		return jsonMapper.writeValueAsString(reponse);
	
	}
}
