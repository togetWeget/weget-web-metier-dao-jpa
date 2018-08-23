package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.entites.TypeStatut;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAppRoleMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IMembreMetier;

import ci.weget.web.modeles.PostAjoutDetailBlock;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class MembreController {

	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private IAppRoleMetier roleMetier;
	@Autowired
	private IBlocksMetier blocksMetier;

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
			personne = membreMetier.findById(id);

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

	private Reponse<Personne> getMembreByLogin(final String login) {
		Personne personne = null;
		try {
			personne = membreMetier.findByLogin(login);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	// recuprer le block a partir de son identifiant
	private Reponse<Block> getBlock(final Long id) {
		// on récupère le block
		Block block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Block>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Block>(2, messages, null);
		}
		// ok
		return new Reponse<Block>(0, null, block);
	}

	// enregistrer un membre dans la base de donnee
	@PostMapping("/membres")
	public String creer(@RequestBody Personne entite) throws JsonProcessingException {
		Reponse<Personne> reponse;
		Reponse<Personne> reponse2;
		try {
			TypeStatut abonne= new TypeStatut("membre", "");
			entite.setTypestatut(abonne);
			Personne p1 = membreMetier.creer(entite);
			// mettre le statut a membre
			reponse2 = getMembreById(p1.getId());
			// recuperer la personne
			Personne p3 = reponse2.getBody();
			// recupere le role qui a pour nom membre
			AppRoles roleMembre = roleMetier.findRoleByNom("membre");

			membreMetier.addRoleToUser(p3.getLogin(), roleMembre.getNom());

			// membreMetier.modifier(p3);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes avec statut membres", p1.getLogin()));
			reponse = new Reponse<Personne>(0, messages, p3);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	
	// obtenir la liste des memmbres
	@GetMapping("/typePersonnes/{type}")
	public String findAllTypePersonne(@PathVariable("type") String type) throws JsonProcessingException {
		Reponse<List<Personne>> reponse;

		try {
			List<Personne> personneTous = membreMetier.personneALL(type);
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<Personne>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<Personne>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	
	// recherche les membres par login
	@GetMapping("/membresLogin/{login}")
	public String chercherMembresParLogin(@PathVariable String login) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Personne> reponse = null;
		reponse = getMembreByLogin(login);
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

	@PostMapping("/photoAbonnes")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLogin;
		// recuperer le libelle à partir du nom de la photo
		String login = file.getOriginalFilename();
		reponseParLogin = getMembreByLogin(login);
		Personne p = reponseParLogin.getBody();
		System.out.println(p);

		String path = "http://localhost:8080/getPhotoMembre/" + p.getVersion() + "/" + login;
		System.out.println(path);
		if (reponseParLogin.getStatut() == 0) {
			String dossier = togetImage + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				p.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + login));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getLogin()));
				reponse = new Reponse<Personne>(0, messages, membreMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personne>(reponseParLogin.getStatut(), reponseParLogin.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoMembre/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
