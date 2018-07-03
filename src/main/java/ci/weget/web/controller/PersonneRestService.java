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

import ci.weget.web.entites.Membres;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IPersonneMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.security.UserRoles;
import ci.weget.web.utilitaires.RegisterForm;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonneRestService {

	@Autowired
	private IPersonneMetier personneMetier;
	

	@Autowired
	private ObjectMapper jsonMapper;
	
	////////////////////////////////////////////////////////////////
  ////////////////	Rechercher une personne a partir de son identifiant///////////

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
	////////////////////////////////////////////////////////////////////////////////////////
	//////////// rechercher une personne par son login/////////////////////////////////////
	private Reponse<Personnes> getPersonneByLogin(String login) {
		Personnes personne = null;
		try {
			personne = personneMetier.findByLogin(login);
		} catch (RuntimeException e) {
			new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la personne n'exixte pas", login));
			return new Reponse<Personnes>(2, messages, null);
		}
		return new Reponse<Personnes>(0, null, personne);
	}
/////////////////////////////////////////////////////////////////////////////////////////
	///////////////enregistrer un membre dans la base de donnee//////////////////////
	
	@PostMapping("/registerMembres")
	public String register(@RequestBody Personnes personne) throws  JsonProcessingException  {
	    Reponse<Personnes> reponse= null;
		
	try {
			Personnes p1=  personneMetier.creer(personne);
			AppRoles appRole = new AppRoles("USER");
			UserRoles userRole = new UserRoles(p1,appRole);
			personneMetier.saveRole(appRole);
			personneMetier.saveUserRole(userRole);
			List<AppRoles> roles = personneMetier.getRoles(p1.getId());
			roles.add(appRole);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", p1.getLogin()));
			reponse = new Reponse<Personnes>(0, messages,p1);
		} catch (InvalideTogetException e) {
			reponse = new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
	
		return jsonMapper.writeValueAsString(reponse);
	}
////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////enregistrer une personne dans la base de donnee////////////////////
	
	@PostMapping("/personnes")
	public String creer(@RequestBody Personnes entite) throws JsonProcessingException {
		Reponse<Personnes> reponse;

		try {

			Personnes p1 = personneMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s  à été créer avec succes", p1.getNom(), p1.getPrenom()));
			reponse = new Reponse<Personnes>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// obtenir une personne personne par son type

	@GetMapping("/personnes/{type}")
	public String findAllTypePersonne(@PathVariable("type") String type) throws JsonProcessingException {
		Reponse<List<Personnes>> reponse;
		reponse = new Reponse<List<Personnes>>(0, null, personneMetier.personneALL(type));

		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("personnes")
	public String modifier(Personnes modif) throws JsonProcessingException {
		Reponse<Personnes> reponsePersModif = null;
		Reponse<Personnes> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getPersonneById(modif.getId());
		if (reponsePersModif.getStatut() == 0) {
			try {
				Personnes p2 = personneMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s %s a modifier avec succes", p2.getNom(), p2.getPrenom()));
				reponse = new Reponse<Personnes>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Personnes>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La personne n'existe pas"));
			reponse = new Reponse<Personnes>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	public Personnes findById(Long id) {
		return personneMetier.findById(id);
	}

	public List<Personnes> findAll() {
		return personneMetier.findAll();
	}

	public List<AppRoles> getRoles(long id) {
		return personneMetier.getRoles(id);
	}

	public List<AppRoles> getRoles(String login, String password) {
		return personneMetier.getRoles(login, password);
	}

	public boolean supprimer(Long id) {
		return personneMetier.supprimer(id);
	}

	public boolean supprimer(List<Personnes> entites) {
		return personneMetier.supprimer(entites);
	}

	public Personnes findByLogin(String login) {
		return personneMetier.findByLogin(login);
	}

	public boolean existe(Long id) {
		return personneMetier.existe(id);
	}

	public List<Personnes> findByType(String type) {
		return personneMetier.findByType(type);
	}

	public Personnes findByNom(String nom) {
		return personneMetier.findByNom(nom);
	}

	public List<Personnes> findAllPersonnesParMc(String type, String mc) {
		return personneMetier.findAllPersonnesParMc(type, mc);
	}

	public List<Personnes> findAllAdministrateurs() {
		return personneMetier.findAllAdministrateurs();
	}

	public List<Personnes> findAllMembres() {
		return personneMetier.findAllMembres();
	}

	public List<Personnes> findByNomCompletContainingIgnoreCase(String nomcomplet) {
		return personneMetier.findByNomCompletContainingIgnoreCase(nomcomplet);
	}

	public List<Personnes> personneALL(String type) {
		return personneMetier.personneALL(type);
	}

}
