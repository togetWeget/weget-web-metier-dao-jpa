package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Commande;
import ci.weget.web.entites.Panier;
import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAppRoleMetier;
import ci.weget.web.metier.IBlocksMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.utilitaires.ApiResponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "*")
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

	private Reponse<Personne> getMembreById(final long id) {
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
	@PostMapping("/signup")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String registerUser(@RequestBody Personne entite) throws Exception {
		Reponse<ResponseEntity<?>> responseEntity;
		Reponse<ResponseEntity<ApiResponse>> responseEntityApi;
		ResponseEntity<ApiResponse> resApi;
		Reponse<Personne> reponse;
		Reponse<Personne> reponse2;
		// creer un utilisateur dans firebase 
		Reponse<UserRecord> reponseFirebase;
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(entite.getLogin());
		// verifier d'abord si l'utiloisateur existe avec son mail ici le login est le mail
		/*if (userRecord.getUid() == null) {
			// si il n'existe pas on le creer
			UserRecord record = membreMetier.createUser(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s firebase a ete creer avec succee", record.getUid()));
			// on retourne l'utilisateur dans la reponse 
			reponseFirebase = new Reponse<UserRecord>(0, messages, record);
		} */

		try {
            // on creer l'utilisateur dans la base de donne mysql
			Personne p1 = membreMetier.creer(entite);

			// mettre le statut a membre
			reponse2 = getMembreById(p1.getId());
			// recuperer la personne
			Personne p3 = reponse2.getBody();
			// recupere le role qui a pour nom membre
			AppRoles roleMembre = roleMetier.findRoleByNom("membre");
           // associe l'utilisateur au role membre
			membreMetier.addRoleToUser(p3.getLogin(), roleMembre.getNom());
			
			URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/api/users/{username}")
	                .buildAndExpand(p1.getLogin()).toUri();
			resApi= ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

			// membreMetier.modifier(p3);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes avec statut membres", p1.getLogin()));
			responseEntity = new Reponse<ResponseEntity<?>>(0, messages, resApi);

		} catch (InvalideTogetException e) {

			responseEntity = new Reponse<ResponseEntity<?>>(1, Static.getErreursForException(e), null);

		}
		return jsonMapper.writeValueAsString(responseEntity);
	}

	// mettre a jour un membre
	@PutMapping("/membres")
	public String modifier(@RequestBody Personne modif) throws JsonProcessingException {
		Reponse<Personne> reponsePersModif = null;
		Reponse<Personne> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getMembreById(modif.getId());
		if (reponsePersModif.getStatus() == 0) {
			try {
				Personne p2 = membreMetier.modifier(modif);
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

	// recherche les membres par login
	@GetMapping("/membres/{id}")
	public String chercherMembresParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Personne> reponse = null;
		reponse = getMembreById(id);
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

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer la phorto d'un membre dans la
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoMembre")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file,@RequestParam String membre_login ) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		System.out.println(libelle);
		reponseParLibelle = getMembreByLogin(membre_login);
		Personne p = reponseParLibelle.getBody();
		System.out.println(p);

		String path = "http://wegetback:8080/getPhotoMembre/" + p.getVersion() + "/" + libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "membres" + "/";
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
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getLogin()));
				reponse = new Reponse<Personne>(0, messages, membreMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personne>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////// enregistrer la photo de couverure dans la
	///////////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////
	@PostMapping("/photoCouvertureMembre")
	public String creerPhotoCouverture(@RequestParam(name = "image_photo") MultipartFile file,@RequestParam String membre_login) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getMembreByLogin(membre_login);
		Personne p = reponseParLibelle.getBody();
		System.out.println(p);

		String path = "http://wegetback:8080/getPhotoCouvertureMembre/" + p.getVersion() + "/" + libelle;
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "CouvertureMembre" + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				p.setPathPhotoCouveture(path);
				System.out.println(path);
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getLogin()));
				reponse = new Reponse<Personne>(0, messages, membreMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personne>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	///////////////////////////////////////////////////////////////////////////////
	//// recuperer photo d' membre dans la base /////////////////////////////////
	@GetMapping(value = "/getPhotoMembre/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosMembre(@PathVariable String version, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/" + "membres" + "/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
	//////// recuperer la photo de couverture pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoCouvertureMembre/{version}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable String libelle)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/" + "/" + "CouvertureMembre" + "/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

	@PostMapping("/fichierCv")
	public String creerFichierCv(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getMembreByLogin(libelle);
		Personne p = reponseParLibelle.getBody();
		System.out.println(p);

		String path = "http://wegetback:8080/getFichierCv/" + p.getVersion() + "/" + p.getId();
		System.out.println(path);
		if (reponseParLibelle.getStatus() == 0) {
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
				file.transferTo(new File(dossier + p.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getLogin()));
				reponse = new Reponse<Personne>(0, messages, membreMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personne>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//// enregistrer le cv d'une personne dans la base
	@GetMapping(value = "/getFichierCv/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getCvAbonne(@PathVariable String version, @PathVariable Long id)
			throws FileNotFoundException, IOException {

		// Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle);
		// Blocks b = personneLibelle.getBody();
		System.out.println(version);
		String dossier = togetImage + "/";
		File f = new File(dossier + id);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}
}
