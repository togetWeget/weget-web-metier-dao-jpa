package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.UserRoles;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	private IAdminMetier adminMetier;
	@Autowired
	private IMembreMetier membreMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	////////////////////////////////////////////////////////////////
	//////////////// Rechercher une personne a partir de son identifiant///////////

	private Reponse<Personne> getPersonneById(final Long id) {
		Personne personne = null;
		try {
			personne = adminMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	////////////////////////////////////////////////////////////////////////////////////////
	//////////// rechercher une personne par son
	//////////////////////////////////////////////////////////////////////////////////////// login/////////////////////////////////////
	private Reponse<Personne> getPersonneByLogin(String login) {
		Personne personne = null;
		try {
			personne = adminMetier.findPersonnesByLogin(login);
		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la personne n'exixte pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// enregistrer une personne dans la base de
	//////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////

	@PostMapping("/admin")
	public String creer(@RequestBody Personne entite) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne p1 = adminMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personne>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/admin/{type}")
	public String findAllTypePersonne(@PathVariable("type") String type) throws JsonProcessingException {
		Reponse<List<Personne>> reponse;
		reponse = new Reponse<List<Personne>>(0, null, adminMetier.personneALL(type));

		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/admin")
	public String modifier(Personne modif) throws JsonProcessingException {
		Reponse<Personne> reponsePersModif = null;
		Reponse<Personne> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getPersonneById(modif.getId());
		if (reponsePersModif.getStatus() == 0) {
			try {
				Personne p2 = adminMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s %s a modifier avec succes", p2.getNom(), p2.getPrenom()));
				reponse = new Reponse<Personne>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La personne n'existe pas"));
			reponse = new Reponse<Personne>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/personnesparId/{id}")
	public String chercherPersonneParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Personne> reponse = null;

		reponse = getPersonneById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer une personne
	@DeleteMapping("/admin/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Personne p = null;
		if (!erreur) {
			Reponse<Personne> responseSup = getPersonneById(id);
			p = responseSup.getBody();
			if (responseSup.getStatus() != 0) {
				reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  %s a ete supprime", p.getId(), p.getNom(), p.getPrenom()));

				reponse = new Reponse<Boolean>(0, messages, adminMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// supprimer un membre
		@DeleteMapping("/adminMembres/{id}")
		public String supprimerMemmbre(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;
			boolean erreur = false;
			Personne p = null;
			if (!erreur) {
				Reponse<Personne> responseSup = getPersonneById(id);
				p = responseSup.getBody();
				if (responseSup.getStatus() != 0) {
					reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
					erreur = true;

				}
			}
			if (!erreur) {
				// suppression
				try {

					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s  %s a ete supprime", p.getId(), p.getNom(), p.getPrenom()));

					reponse = new Reponse<Boolean>(0, messages, membreMetier.supprimer(id));

				} catch (RuntimeException e1) {
					reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
				}
			}
			return jsonMapper.writeValueAsString(reponse);
		}

////////////les roles dúne personne par id////////////////////////////////////
	/*@GetMapping("/roleParPersonne/{id}")
	public String getRoleParPersonneId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<UserRoles>> reponse;
		try {
			List<UserRoles> ur = adminMetier.roleParPersonneId(id);
			reponse = new Reponse<List<UserRoles>>(0, null, ur);
		} catch (Exception e) {
			reponse = new Reponse<List<UserRoles>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}*/
}
