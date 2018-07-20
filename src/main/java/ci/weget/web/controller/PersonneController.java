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

import ci.weget.web.entites.Blocks;
import ci.weget.web.entites.Personnes;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IPersonneMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.security.AppRoles;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonneController {

	@Autowired
	private IPersonneMetier personneMetier;
	

	@Autowired
	private ObjectMapper jsonMapper;
	
////////////chemin ou sera sauvegarder les photos
//////////// ////////////////////////////////////////
@Value("${dir.images}")
private String togetImage;
	
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
			personne = personneMetier.findPersonnesByLogin(login);
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

		
////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////enregistrer une personne dans la base de donnee////////////////////
	
	@PostMapping("/personnes")
	public String creer(@RequestBody Personnes entite) throws JsonProcessingException {
		Reponse<Personnes> reponse;
           try {
            Personnes p1 = personneMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personnes>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personnes>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	//////////////// creer un abonne
	@PostMapping("/Abonnes")
	public String creerAbonne(@RequestBody Personnes personne) throws JsonProcessingException {
		Reponse<Personnes> reponse;
           try {
            Personnes p1 = personneMetier.creerAbonne(personne);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personnes>(0, messages, p1);

		} catch (Exception e) {

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

	@PutMapping("/personnes")
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
	@PostMapping("/photoPersonne")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Personnes> reponse = null;
		Reponse<Personnes> reponseParLogin;
		// recuperer le libelle à partir du nom de la photo
		String login = file.getOriginalFilename();
		reponseParLogin = getPersonneByLogin(login);
		Personnes p = reponseParLogin.getBody();
		System.out.println(p);

		String path = "http://localhost:8080/getPhotoPersonne/"+ p.getVersion()+"/" + login;
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
				messages.add(String.format("%s (photo ajouter avec succes)", p.getNomComplet()));
				reponse = new Reponse<Personnes>(0, messages, personneMetier.modifier(p));

			} catch (Exception e) {

				reponse = new Reponse<Personnes>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<Personnes>(reponseParLogin.getStatut(), reponseParLogin.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoPersonne/{version}/{login}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable String login)
			throws FileNotFoundException, IOException {
		
		 // Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle); 
		  //Blocks b = personneLibelle.getBody(); 
		  System.out.println(version); 
		  String dossier = togetImage+"/"; 
		  File f = new File(dossier+login); 
		  byte[] img = IOUtils.toByteArray(new FileInputStream(f));
		 
		return img;
	}

	@GetMapping("/personnesparId/{id}")
	public String chercherPersonneParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Personnes> reponse = null;

		reponse = getPersonneById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	public List<Personnes> findAll() {
		return personneMetier.findAll();
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

	
	public boolean existe(Long id) {
		return personneMetier.existe(id);
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
