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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.Personne;
import ci.weget.web.metier.IAbonneMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AbonneController {
	@Autowired
	private IAbonneMetier abonneMetier;
	@Autowired
	private IMembreMetier membreMetier;

	@Autowired
	private ObjectMapper jsonMapper;

	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;
	
		
		////////////////////////////////////////////////////////////////
	  ////////////////	Rechercher une personne a partir de son identifiant///////////

		private Reponse<Personne> getAbonneById(final Long id) {
			Personne personne = null;
			try {
				personne = abonneMetier.findById(id);
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
		//////////// rechercher un abonne par son login/////////////////////////////////////
		private Reponse<Personne> getAbonneByLogin(String login) {
			Personne personne = null;
			try {
				personne = abonneMetier.findAbonneByLogin(login);
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

			
		// recherche les Abonnes par identifiant
		@GetMapping("/Abonnes/{id}")
		public String chercherAbonneParId(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<Personne>> reponse;
			try {
				List<Personne> db = membreMetier.chercherAbonneParId(id);
				reponse = new Reponse<List<Personne>>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	@PostMapping("/photoAbonne")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponseParLogin;
		// recuperer le libelle à partir du nom de la photo
		String login = file.getOriginalFilename();
		reponseParLogin = getAbonneByLogin(login);
		Personne p = reponseParLogin.getBody();
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
				// enregistrer le chemin de la photo
				p.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + login));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", p.getNomComplet()));
				reponse = new Reponse<Personne>(0, messages, abonneMetier.modifierAbonne(p));

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

}
