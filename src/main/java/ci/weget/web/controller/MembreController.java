package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Commande;
import ci.weget.web.entites.DetailBlock;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Panier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MembreController {

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

	private Reponse<Personne> getMembreById(final Long id) {
		Personne personne = null;
		try {
			personne = membreMetier.personneParId(id);
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

	// enregistrer un membre dans la base de donnee
	@PostMapping("/membres")
	public String creer(@RequestBody Personne entite) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne p1 = membreMetier.ajouterMembres(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personne>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// faire la mise a jour du profil d'un membre
	@PutMapping("/membres")
	public String modifier(Personne modif) throws JsonProcessingException {
		Reponse<Personne> reponsePersModif = null;
		Reponse<Personne> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getMembreById(modif.getId());
		if (reponsePersModif.getStatut() == 0) {
			try {
				Personne p2 = membreMetier.metteAjourSonProil(modif);
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

	// recherche les Abonnes par competence
	@GetMapping("/membres/{competence}")
	public String chercherPersonneParCompetence(@PathVariable String competence) throws JsonProcessingException {
		Reponse<List<Personne>> reponse;
		try {
			List<Personne> db = membreMetier.chercherPersonneParCompetence(competence);
			reponse = new Reponse<List<Personne>>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	
	// enregistrer une commande
	@PostMapping("/commande")
	public String enregistrerCommande(@RequestBody Panier p, @RequestBody Personne pers)
			throws JsonProcessingException {
		Reponse<Commande> reponse;
		try {
			Commande p1 = membreMetier.enregistrerCommande(p, pers);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", pers.getLogin()));
			reponse = new Reponse<Commande>(0, messages, p1);

		} catch (Exception e) {

			reponse = new Reponse<Commande>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
