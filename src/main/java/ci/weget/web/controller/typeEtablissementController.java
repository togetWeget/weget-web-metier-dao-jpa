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

import ci.weget.web.entites.Abonnement;
import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.TypeEtablissement;
import ci.weget.web.entites.combo.Specialite;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ITypeEtablissementMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class typeEtablissementController {
	@Autowired
	private ITypeEtablissementMetier typeEtablissementMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer une categoryBlock a partir de son identifiant
	private Reponse<TypeEtablissement> getTypeEtablissementById(Long id) {
		TypeEtablissement catBlock = null;
		try {
			catBlock = typeEtablissementMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (catBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<TypeEtablissement>(0, null, catBlock);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	///////////////// recuperer unn block a partir de son libelle
	///////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////

	private Reponse<TypeEtablissement> getCategoryBlockParLibellle(String libelle) {
		TypeEtablissement catBlock = null;
		try {
			catBlock = typeEtablissementMetier.rechercheParLibelle(libelle);
		} catch (RuntimeException e) {
			new Reponse<TypeEtablissement>(1, Static.getErreursForException(e), null);
		}
		if (catBlock == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le block n'exixte pas", libelle));
			return new Reponse<TypeEtablissement>(2, messages, null);
		}
		return new Reponse<TypeEtablissement>(0, null, catBlock);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/typeEtablissement")
	public String creer(@RequestBody TypeEtablissement catBlock) throws JsonProcessingException {
		Reponse<TypeEtablissement> reponse;

		try {

			TypeEtablissement b1 = typeEtablissementMetier.creer(catBlock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", b1.getLibelle()));
			reponse = new Reponse<TypeEtablissement>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<TypeEtablissement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////
	
	@PutMapping("/typeEtablissement")
	public String modfierUnBlock(@RequestBody TypeEtablissement modif) throws JsonProcessingException {
		Reponse<TypeEtablissement> reponsePersModif = null;
		Reponse<TypeEtablissement> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getTypeEtablissementById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				TypeEtablissement b2 = typeEtablissementMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getLibelle()));
				reponse = new Reponse<TypeEtablissement>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<TypeEtablissement>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<TypeEtablissement>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer un abonne par son identifiant
		@GetMapping("/typeEtablissement/{id}")
		public String profilAbonneId(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<TypeEtablissement> reponse = null;

			reponse = getTypeEtablissementById(id);

			return jsonMapper.writeValueAsString(reponse);

		}
		

		// recuperer touts les pays
		@GetMapping("/typeEtablissement")
		public String findAll() throws JsonProcessingException, InvalideTogetException {
			Reponse<List<TypeEtablissement>> reponse;
			try {
				List<TypeEtablissement> p = typeEtablissementMetier.findAll();
				reponse = new Reponse<List<TypeEtablissement>>(0, null, p);
			} catch (Exception e) {
				reponse = new Reponse<List<TypeEtablissement>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	@PostMapping("/phototypeEtablissement")
	public String creerPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<TypeEtablissement> reponse = null;
		Reponse<TypeEtablissement> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		reponseParLibelle = getCategoryBlockParLibellle(libelle);
		TypeEtablissement b = reponseParLibelle.getBody();
		System.out.println(b);

		String path = "http://wegetback:8080/getPhotoTypeEtablissement/"+ b.getVersion()+"/" + b.getId();
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
				b.setPhathphoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + b.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", b.getLibelle()));
				reponse = new Reponse<TypeEtablissement>(0, messages, typeEtablissementMetier.modifier(b));

			} catch (Exception e) {

				reponse = new Reponse<TypeEtablissement>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("cette personne n'existe pas"));
			reponse = new Reponse<TypeEtablissement>(reponseParLibelle.getStatus(), reponseParLibelle.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoTypeEtablissement/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable String version, @PathVariable Long id)
			throws FileNotFoundException, IOException {
		
		 // Reponse<Blocks> personneLibelle = getBlockParLibellle(libelle); 
		  //Blocks b = personneLibelle.getBody(); 
		  System.out.println(version); 
		  String dossier = togetImage+"/"; 
		  File f = new File(dossier+id); 
		  byte[] img = IOUtils.toByteArray(new FileInputStream(f));
		 
		return img;
	}
}
