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
import org.springframework.security.access.annotation.Secured;
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

import ci.weget.web.entites.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.JwtTokenProvider;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "*")
public class MembreController {

	@Autowired
	private IMembreMetier membreMetier;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;
////////////chemin ou sera sauvegarder les photos
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

	@PostMapping("/membre")
	public String creer(@RequestBody Personne entite) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne p1 = membreMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personne>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/membre")
	public String modifier(Personne modif) throws JsonProcessingException {
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

	// recherche les membres par login
	@PutMapping("/membreStatut")
	public String MisAjourMembre(@RequestBody Personne personne) throws JsonProcessingException {
		Reponse<Personne> reponse;
		Reponse<Personne> reponsep;

		try {

			// creerAbonne.creerUnAbonne(personne);
			Personne b = membreMetier.modifier(personne);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" a été mis à jour avec succes"));
			reponse = new Reponse<Personne>(0, messages, b);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir la liste des membres
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

	// recherche les membres par login
	
	@PostMapping("/abonneSpecial")
	public String abonneSpecial(@RequestBody Personne personne) throws JsonProcessingException {
		Reponse<Boolean> reponse;
		Reponse<Personne> reponsep;

		try {

			// creerAbonne.creerUnAbonne(personne);
			boolean b = membreMetier.creerAbonne(personne);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Boolean>(0, messages, b);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer la phorto d'un membre dans la
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoMembre")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam String membre_login)
			throws Exception {
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
	public String creerPhotoCouverture(@RequestParam(name = "image_photo") MultipartFile file,
			@RequestParam String membre_login) throws Exception {
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
